package privateProtocol;


import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public final class MarshallingCodeCFacotry {

	public static NettyMarshallingDecoder buildMarshallingDecoder(){
		MarshallerFactory marshallingFactory=Marshalling.getProvidedMarshallerFactory("serial");
	    MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider=new DefaultUnmarshallerProvider(marshallingFactory, configuration);
		NettyMarshallingDecoder decoder=new NettyMarshallingDecoder(provider,1024);
		return decoder;
	}
	
	public static NettyMarshallingEncoder buildMarshllingEncoder(){
		MarshallerFactory marshallerFactory=Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider=new DefaultUnmarshallerProvider(marshallerFactory,configuration);
		NettyMarshallingEncoder encoder=new NettyMarshallingEncoder(provider,1024);
		return encoder;
	}
}
