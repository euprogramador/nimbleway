package br.com.aexo.nimbleway.core.subprotocols.json.decoder;


import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.core.messages.UnregisteredMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
class UnregisteredMessageJsonDecoder implements JsonDecoderMessage<UnregisteredMessage> {

	@Override
	public UnregisteredMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long idRegistration = raw.get(1).asLong();

		return new UnregisteredMessage(idRegistration);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(67).equals(messageIdType);
	}

}
