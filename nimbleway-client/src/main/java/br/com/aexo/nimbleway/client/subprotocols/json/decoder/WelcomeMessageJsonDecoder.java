package br.com.aexo.nimbleway.client.subprotocols.json.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.messages.WelcomeMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonDecoderMessage;

public class WelcomeMessageJsonDecoder implements JsonDecoderMessage<WelcomeMessage> {

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(2).equals(messageIdType);
	}

	@Override
	public WelcomeMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		Long sessionId = raw.get(1).asLong();
		JsonNode jsonNode = raw.get(2).get("agent");
		String agent = jsonNode != null ? jsonNode.asText() : null;

		return new WelcomeMessage(sessionId, agent);
	}

}
