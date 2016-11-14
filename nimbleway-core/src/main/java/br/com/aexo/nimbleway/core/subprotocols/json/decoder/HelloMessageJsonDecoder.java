package br.com.aexo.nimbleway.core.subprotocols.json.decoder;

import br.com.aexo.nimbleway.core.messages.HelloMessage;
import br.com.aexo.nimbleway.core.messages.WelcomeMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class HelloMessageJsonDecoder implements JsonDecoderMessage<HelloMessage> {

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(1).equals(messageIdType);
	}

	@Override
	public HelloMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		String realm = raw.get(1).asText();
		return new HelloMessage(realm);
	}

}
