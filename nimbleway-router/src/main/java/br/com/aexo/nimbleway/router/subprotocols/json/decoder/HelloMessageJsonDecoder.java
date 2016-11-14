package br.com.aexo.nimbleway.router.subprotocols.json.decoder;

import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.router.messages.HelloMessage;
import br.com.aexo.nimbleway.router.subprotocols.json.JsonDecoderMessage;

public class HelloMessageJsonDecoder implements JsonDecoderMessage<HelloMessage>{

	@Override
	public HelloMessage decode(Object o) {
		
		ArrayNode raw = (ArrayNode) o;
		
		String realm = raw.get(2).asText();
		
		return new HelloMessage(realm);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return messageIdType.equals(1);
	}

}
