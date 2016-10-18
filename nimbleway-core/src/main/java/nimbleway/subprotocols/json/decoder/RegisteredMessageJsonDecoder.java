package nimbleway.subprotocols.json.decoder;


import nimbleway.messages.RegisteredMessage;
import nimbleway.subprotocols.json.JsonDecoderMessage;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class RegisteredMessageJsonDecoder implements JsonDecoderMessage<RegisteredMessage> {

	@Override
	public RegisteredMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long idRequest = raw.get(1).asLong();
		Long idRegistration = raw.get(2).asLong();

		return new RegisteredMessage(idRequest, idRegistration);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(65).equals(messageIdType);
	}

}
