package br.com.aexo.nimbleway.core.subprotocols.json;


import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.core.messages.UnsubscribedMessage;

class UnsubscribedMessageJsonDecoder implements JsonDecoderMessage<UnsubscribedMessage> {

	@Override
	public UnsubscribedMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long idRegistration = raw.get(1).asLong();

		return new UnsubscribedMessage(idRegistration);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(35).equals(messageIdType);
	}

}
