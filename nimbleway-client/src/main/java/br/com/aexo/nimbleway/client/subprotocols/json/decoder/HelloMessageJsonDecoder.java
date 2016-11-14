package br.com.aexo.nimbleway.client.subprotocols.json.decoder;

import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.messages.HelloMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonDecoderMessage;

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
