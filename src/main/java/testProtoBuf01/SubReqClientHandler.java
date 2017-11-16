package testProtoBuf01;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter{

	public SubReqClientHandler(){
		
	}

	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i=0;i<10;i++){
			ctx.write(subReq(i));
		}
		ctx.flush();
	}
    public SubscribeReqProto.SubscribeReq subReq(int i){
    	SubscribeReqProto.SubscribeReq.Builder  builder=SubscribeReqProto.SubscribeReq.newBuilder();
    	builder.setSubReqID(i);
    	builder.setUserName("Lilinfeng");
    	builder.setProcductName("Nettty for protobuf");
    	List<String> list=new ArrayList<String>();
    	list.add("beijing");
    	list.add("tianjin");
    	list.add("haerbin");
    	builder.addAllAddress(list);
    	return builder.build();
    }
	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(" client receive message :"+msg);
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
