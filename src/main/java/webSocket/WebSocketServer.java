package webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {

	public void run(int port) throws InterruptedException{
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try{
			ServerBootstrap b=new ServerBootstrap();
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					 ChannelPipeline pipeline=ch.pipeline();
					 pipeline.addLast("http-codec",new HttpServerCodec());//将请求或者应答的消息编码或者解码为HTTp消息
					 pipeline.addLast("aggregator",new HttpObjectAggregator(65536));//将http消息的多个组成部分组织成一条完整的http消息
					 pipeline.addLast("http-chunked",new ChunkedWriteHandler());//想客户端发送html5 文件
					 pipeline.addLast("handler",new WebSocketServerHandler());
					
				}
				
			});
			Channel ch=b.bind(port).sync().channel();
			System.out.println("Web Socket server started at port :"+port+".");
			System.out.println("Open Your brower and navigate to http://localhost:"+port+"/");
			ch.closeFuture().sync();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws InterruptedException{
		int port=8078;
		new WebSocketServer().run(port);
	}
}










