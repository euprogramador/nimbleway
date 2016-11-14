package br.com.aexo.nimbleway.client.subprotocols.json.decoder;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.messages.ResultMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonDecoderMessage;

public class ResultMessageJsonDecoder implements JsonDecoderMessage<ResultMessage> {

	@Override
	public ResultMessage decode(Object o) {
		ObjectMapper mapper = new ObjectMapper();

		ArrayNode raw = (ArrayNode) o;
		Long callId = raw.get(1).asLong();
		@SuppressWarnings("unchecked")
		Map<String, Object> details = mapper.convertValue(raw.get(2), Map.class);
		ArrayNode params = (ArrayNode) raw.get(3);
		JsonNode payload = raw.get(4);

		return new ResultMessage(callId, details, params, payload);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(50).equals(messageIdType);
	}

}
