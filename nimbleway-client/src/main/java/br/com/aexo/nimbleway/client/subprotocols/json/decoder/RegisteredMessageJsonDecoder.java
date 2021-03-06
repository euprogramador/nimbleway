package br.com.aexo.nimbleway.client.subprotocols.json.decoder;


import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.messages.RegisteredMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonDecoderMessage;

public class RegisteredMessageJsonDecoder implements JsonDecoderMessage<RegisteredMessage> {

	@Override
	public RegisteredMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long requestId = raw.get(1).asLong();
		Long registrationId = raw.get(2).asLong();

		return new RegisteredMessage(requestId, registrationId);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(65).equals(messageIdType);
	}

}
