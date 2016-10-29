package br.com.aexo.nimbleway.subprotocols.json.decoder;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.EventMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class EventMessageJsonDecoder implements
		JsonDecoderMessage<EventMessage> {

	@Override
	public EventMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		Long idRequest = raw.get(2).asLong();
		Long idFunctionRegistration = raw.get(1).asLong();
		ArrayNode params = (ArrayNode) raw.get(4);
		return new EventMessage(idRequest, idFunctionRegistration, params);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(36).equals(messageIdType);
	}
}
