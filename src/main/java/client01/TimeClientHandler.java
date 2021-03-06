package client01;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler  extends ChannelHandlerAdapter{

	private static final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());
	private  ByteBuf firstMessage;
	public TimeClientHandler(){
		byte[] req="query time order".getBytes();
		firstMessage=Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf=(ByteBuf)msg;
		byte[] req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req,"UTF-8");
		System.out.println("现在的时间是："+body);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("未知的程序错误："+cause.getMessage());
		ctx.close();
	}
	

	
}
