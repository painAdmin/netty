package testProtoBuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeReqProto {

	private static byte[] encode(SubscribeReqProto.SubscribeReq req){
		return req.toByteArray();
	}
	
	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException{
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}
	
	private static SubscribeReqProto.SubscribeReq createSubcribeReq(){
		SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("Lilinfeng");
		builder.setProcductName("netty book");
		List<String> address=new ArrayList<String>();
		address.add("beijing");
		address.add("haerbin");
		address.add("tianjin");
		builder.addAllAddress(address);
		return builder.build();
	}
	public static void main(String[] args) throws InvalidProtocolBufferException{
		SubscribeReqProto.SubscribeReq req=createSubcribeReq();
		System.out.println("before encode :"+req.toString());
		byte[] port=encode(req);
		System.out.println("encode :"+ port);
		SubscribeReqProto.SubscribeReq receive=decode(port);
		System.out.println("after decode "+receive.toString());
	}
}











