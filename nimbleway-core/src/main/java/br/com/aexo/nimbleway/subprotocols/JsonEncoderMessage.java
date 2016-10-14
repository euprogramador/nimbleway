package br.com.aexo.nimbleway.subprotocols;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.EncoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.HelloMessage;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.YieldMessage;

public class JsonEncoderMessage implements EncoderMessage {

	Map<Class<? extends WampMessage>,EncoderMessage> encoders = new HashMap<>();

	public JsonEncoderMessage() {

		encoders.put(HelloMessage.class, new HelloMessageJsonEncoder());
		encoders.put(RegisterMessage.class, new RegisterMessageJsonEncoder());
		encoders.put(CallMessage.class, new CallMessageJsonEncoder());
		encoders.put(YieldMessage.class, new YieldMessageJsonEncoder());
	
	}
	
	@Override
	public Object encode(WampMessage wampMessage) {

		EncoderMessage encoderMessage = encoders.get(wampMessage.getClass());
		if (encoderMessage == null) {
			System.out.println("Não detectado:" + wampMessage);
			return null;
		}
		return encoderMessage.encode(wampMessage);
	}

}
