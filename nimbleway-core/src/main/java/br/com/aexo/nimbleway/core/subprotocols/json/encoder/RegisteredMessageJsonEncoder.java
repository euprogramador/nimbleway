package br.com.aexo.nimbleway.core.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import br.com.aexo.nimbleway.core.messages.RegisteredMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class RegisteredMessageJsonEncoder implements JsonEncoderMessage<RegisteredMessage> {

	@Override
	public Object encode(RegisteredMessage msg) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(65);
			node.add(msg.getRequestId());
			node.add(msg.getRegistrationId());

			StringWriter writer = new StringWriter();

			mapper.writeValue(writer, node);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof RegisteredMessage;
	}

}
