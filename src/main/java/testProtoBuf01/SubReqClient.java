package testProtoBuf01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SubReqClient {

	public void connect(String host,int port) throws InterruptedException{
		EventLoopGroup  group=new NioEventLoopGroup();
		try{
			Bootstrap b=new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(SubcribeRespProto.SubscribeResp.getDefaultInstance()));
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new SubReqClientHandler());
				}
				
			});
			ChannelFuture f=b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
 	}
	public static void main(String[] args) throws InterruptedException{
		int port=8078;
		String host="127.0.0.1";
		new SubReqClient().connect(host, port);
	}
}
