package br.com.aexo.nimbleway.subprotocols;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.DecoderMessage;
import br.com.aexo.nimbleway.WampMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonDecoderMessage implements DecoderMessage {

	private Map<Integer, DecoderMessage> decoders = new HashMap<>();

	public JsonDecoderMessage() {
		decoders.put(2,new WelcomeMessageJsonDecoder());
		decoders.put(65,new RegisteredMessageJsonDecoder());
		decoders.put(68,new InvocationMessageJsonDecoder());
		decoders.put(50,new ResultMessageJsonDecoder());
	}
	
	@Override
	public WampMessage decode(Object o) {
		try {
			
			
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode node = (ArrayNode) mapper.readTree(o.toString());
			DecoderMessage decoder  = decoders.get(node.get(0).asInt());
			if (decoder==null) {
				System.out.println("Não detectado:" + o);
				return null;
			}
			return decoder.decode(node);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
