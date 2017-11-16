package testProtoBuf01;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHander extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReqProto.SubscribeReq req=(SubscribeReqProto.SubscribeReq)msg;
		String username=req.getUserName();
		System.out.println("用户名称："+username);
		System.out.println("msg"+req.toString());
		if("Lilinfeng".equals(req.getUserName())){
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
		
		
	}

	public SubcribeRespProto.SubscribeResp resp(int subReqID){
		SubcribeRespProto.SubscribeResp.Builder builder=SubcribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setRespCode(0);
		builder.setDesc("Netty book order succeed ,3 days laster ,send to the designate address");
		return builder.build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	     cause.printStackTrace();
	     ctx.close();
	}
	
}
