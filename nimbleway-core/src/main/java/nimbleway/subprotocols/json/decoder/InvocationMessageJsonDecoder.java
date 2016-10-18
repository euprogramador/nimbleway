package nimbleway.subprotocols.json.decoder;

import org.springframework.stereotype.Component;

import nimbleway.messages.InvocationMessage;
import nimbleway.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class InvocationMessageJsonDecoder implements
		JsonDecoderMessage<InvocationMessage> {

	@Override
	public InvocationMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		Long idRequest = raw.get(1).asLong();
		Long idFunctionRegistration = raw.get(2).asLong();
		ArrayNode params = (ArrayNode) raw.get(4);
		return new InvocationMessage(idRequest, idFunctionRegistration, params);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(68).equals(messageIdType);
	}
}
