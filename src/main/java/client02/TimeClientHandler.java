package client02;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler  extends ChannelHandlerAdapter{

	private static final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());
	private  ByteBuf firstMessage;
	private int counter;
	private byte[] req;
	public TimeClientHandler(){
		 req=("query time order"+System.getProperty("line.sepatator")).getBytes();
	
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf messgae=null;
		for(int i=0;i<100;i++){
			messgae=Unpooled.buffer(req.length);
			messgae.writeBytes(req);
			ctx.writeAndFlush(messgae);
		}
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf=(ByteBuf)msg;
//		byte[] req=new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body=new String(req,"UTF-8");
		String body=(String)msg;
		System.out.println("现在的时间是："+body+" ; the counter is : "+ ++counter);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("未知的程序错误："+cause.getMessage());
		ctx.close();
	}
	

	
}
