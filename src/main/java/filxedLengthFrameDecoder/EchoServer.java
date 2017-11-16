package filxedLengthFrameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

	public void bind(int port)throws Exception{
		//配置服务端的NIO线程组
		// bossGroup :用于接受发来的连接请求
		// workGroup:用于处理boss接受并且注册给work的连接中的信息
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try{
			ServerBootstrap b=new ServerBootstrap();
			     b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
			     .option(ChannelOption.SO_BACKLOG, 100)
			     .handler(new LoggingHandler(LogLevel.INFO))
			     .childHandler(new ChannelInitializer<SocketChannel>(){

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new FixedLengthFrameDecoder(20)); 
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new EchoServerHandler());
						
					}
			    	 
			     });
			   ChannelFuture f=b.bind(port).sync();  
			   f.channel().closeFuture().sync();
		}finally{
			//优雅关闭，释放资源
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws Exception{
		int port=8078;
		new EchoServer().bind(port);
	}
}















