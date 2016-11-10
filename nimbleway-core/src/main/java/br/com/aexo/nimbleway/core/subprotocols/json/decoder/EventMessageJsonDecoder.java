package br.com.aexo.nimbleway.core.subprotocols.json.decoder;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.core.messages.EventMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
class EventMessageJsonDecoder implements JsonDecoderMessage<EventMessage> {

	@Override
	public EventMessage decode(Object o) {
		ObjectMapper mapper = new ObjectMapper();

		ArrayNode raw = (ArrayNode) o;
		Long subscriptionId = raw.get(1).asLong();
		Long publicationId = raw.get(2).asLong();
		@SuppressWarnings("unchecked")
		Map<String, Object> options = mapper.convertValue(raw.get(3), Map.class);
		ArrayNode params = (ArrayNode) raw.get(4);
		JsonNode payload = raw.get(5);

		return new EventMessage(subscriptionId, publicationId, options, params, payload);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(36).equals(messageIdType);
	}
}
