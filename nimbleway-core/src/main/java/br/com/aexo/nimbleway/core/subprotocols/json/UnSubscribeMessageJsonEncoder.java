package br.com.aexo.nimbleway.core.subprotocols.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.core.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

class UnSubscribeMessageJsonEncoder implements JsonEncoderMessage<UnsubscribeMessage> {

	@Override
	public Object encode(UnsubscribeMessage msg) {

		ObjectMapper mapper = new ObjectMapper();

		ArrayNode node = mapper.createArrayNode();
		node.add(34);

		node.add(msg.getId()); 
		node.add(msg.getSubscription().getId()); 

		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, node);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return writer.toString();
	}

	@Override
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof UnsubscribeMessage;
	}

}
