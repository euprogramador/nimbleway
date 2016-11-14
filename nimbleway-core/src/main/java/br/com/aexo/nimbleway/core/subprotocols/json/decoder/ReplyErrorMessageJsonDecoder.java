package br.com.aexo.nimbleway.core.subprotocols.json.decoder;

import java.util.Map;

import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.messages.ReplyErrorMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Decoder of abort message
 * 
 * @author carlosr
 *
 */
public class ReplyErrorMessageJsonDecoder implements JsonDecoderMessage<ReplyErrorMessage> {

	@Override
	public ReplyErrorMessage decode(Object o) {
		ObjectMapper mapper = new ObjectMapper();

		ArrayNode raw = (ArrayNode) o;

		Integer typeId = raw.get(1).asInt();

		MessageType type = MessageType.forType(typeId);

		Long requestId = raw.get(2).asLong();
		@SuppressWarnings("unchecked")
		Map<String, Object> details = mapper.convertValue(raw.get(3), Map.class);

		String error = raw.get(4).asText();
		ArrayNode params = (ArrayNode) raw.get(5);
		JsonNode payload = raw.get(6);
		
		return new ReplyErrorMessage(type, requestId, details, error,params,payload);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(8).equals(messageIdType);
	}

}
