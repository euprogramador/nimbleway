package br.com.aexo.nimbleway.core.subprotocols.json;


import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.core.messages.PublishedMessage;

class PublishedMessageJsonDecoder implements JsonDecoderMessage<PublishedMessage> {

	@Override
	public PublishedMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long requestId = raw.get(1).asLong();
		Long publicationId = raw.get(2).asLong();

		return new PublishedMessage(requestId, publicationId);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(17).equals(messageIdType);
	}

}
