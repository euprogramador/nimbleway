package br.com.aexo.nimbleway.core.subprotocols.json.decoder;

import java.util.Map;

import br.com.aexo.nimbleway.core.messages.InvocationMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class InvocationMessageJsonDecoder implements
		JsonDecoderMessage<InvocationMessage> {

	@Override
	public InvocationMessage decode(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		
		
		ArrayNode raw = (ArrayNode) o;
		Long requestId = raw.get(1).asLong();
		Long functionId = raw.get(2).asLong();
		@SuppressWarnings("unchecked")
		Map<String, Object> details = mapper.convertValue(raw.get(3), Map.class);
		ArrayNode params = (ArrayNode) raw.get(4);
		JsonNode payload = raw.get(5);
		return new InvocationMessage(requestId,functionId,params,details,payload);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(68).equals(messageIdType);
	}
}
