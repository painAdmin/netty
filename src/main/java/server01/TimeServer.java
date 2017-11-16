package server01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

	public void bind(int port) throws InterruptedException{
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try{
			ServerBootstrap b=new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChildChannelHandler());
			// 绑定端口，同步等待成功
			ChannelFuture f=b.bind(port).sync();
			//等待服务器端监听端口关闭
			f.channel().closeFuture().sync();
		}finally{
			//优雅退出释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	
	}


	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
	
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			socketChannel.pipeline().addLast(new TimeServerHandler());
			
		}
		
	}
	public static void main(String[] args) throws InterruptedException{
		int port=8077;
		try{
			if(args!=null &&args.length>0){
			port=Integer.valueOf(args[0]);	
				
			}
		}catch(NumberFormatException e){
		  //默认值
		}
		
		new TimeServer().bind(port);
	}
}







