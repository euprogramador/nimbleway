
package br.com.aexo.nimbleway.client.subprotocols.json.decoder;


import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.messages.SubscribedMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonDecoderMessage;

public class SubscribedMessageJsonDecoder implements JsonDecoderMessage<SubscribedMessage> {

	@Override
	public SubscribedMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long idRequest = raw.get(1).asLong();
		Long idRegistration = raw.get(2).asLong();

		return new SubscribedMessage(idRequest, idRegistration);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(33).equals(messageIdType);
	}

}
