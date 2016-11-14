package br.com.aexo.nimbleway.core.subprotocols.json.decoder;

import br.com.aexo.nimbleway.core.messages.AbortMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Decoder of abort message
 * 
 * @author carlosr
 *
 */
public class AbortMessageJsonDecoder implements JsonDecoderMessage<AbortMessage> {

	@Override
	public AbortMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		String message = raw.get(1).get("message").asText();
		String wampError = raw.get(2).asText();
		return new AbortMessage(message, wampError);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(3).equals(messageIdType);
	}
	
}
