package br.com.aexo.nimbleway.subprotocols.json.decoder;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.WelcomeMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class WelcomeMessageJsonDecoder implements JsonDecoderMessage<WelcomeMessage> {

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(2).equals(messageIdType);
	}

	@Override
	public WelcomeMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		Long sessionId = raw.get(1).asLong();
		return new WelcomeMessage(sessionId);
	}

}
