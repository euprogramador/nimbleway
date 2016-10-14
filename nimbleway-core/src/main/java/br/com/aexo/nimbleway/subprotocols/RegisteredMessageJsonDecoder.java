package br.com.aexo.nimbleway.subprotocols;

import br.com.aexo.nimbleway.DecoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.RegisteredMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class RegisteredMessageJsonDecoder implements DecoderMessage {

	@Override
	public WampMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long idRequest = raw.get(1).asLong();
		Long idRegistration = raw.get(2).asLong();

		return new RegisteredMessage(idRequest, idRegistration);

	}

}
