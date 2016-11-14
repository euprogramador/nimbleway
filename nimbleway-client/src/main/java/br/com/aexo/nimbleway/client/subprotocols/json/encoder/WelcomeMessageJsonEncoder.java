package br.com.aexo.nimbleway.client.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.messages.WelcomeMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonEncoderMessage;

public class WelcomeMessageJsonEncoder implements JsonEncoderMessage<WelcomeMessage> {

	@Override
	public Object encode(WelcomeMessage msg) {

		ObjectMapper mapper = new ObjectMapper();

		ArrayNode node = mapper.createArrayNode();
		node.add(2);

		node.add(msg.getSessionId());

		ObjectNode roles = mapper.createObjectNode();
		node.add(roles);

		ObjectNode broker = mapper.createObjectNode();
		roles.set("roles", broker);

		ObjectNode features = mapper.createObjectNode();
		broker.set("broker", features);

		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, node);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return writer.toString();
	}
	

	@Override
	public boolean isEncodeOf(ClientMessage type) {
		return type instanceof WelcomeMessage;
	}

}
