package privateProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;

public class NettyMarshallingEncoder extends MarshallingEncoder{

	public NettyMarshallingEncoder(MarshallerProvider provider){
		super(provider);
	}
	@Override
	public void encode(ChannelHandlerContext ctx,Object msg,ByteBuf out){
		super.encode(ctx,msg, out);
	}
}
