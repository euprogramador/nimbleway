package br.com.aexo.nimbleway.client.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.aexo.nimbleway.client.messages.HelloMessage;
import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonEncoderMessage;

public class HelloMessageJsonEncoder implements JsonEncoderMessage<HelloMessage> {

	@Override
	public Object encode(HelloMessage msg) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayNode node = mapper.createArrayNode();
		node.add(1);
		node.add(msg.getReaml());

		ObjectNode roles = mapper.createObjectNode();
		
		ObjectNode internalRoles = mapper.createObjectNode();
		roles.set("roles",internalRoles );
		internalRoles.set("subscriber", mapper.createObjectNode());
		internalRoles.set("publisher", mapper.createObjectNode());
		
		node.add(roles);
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
		return type instanceof HelloMessage;
	}

}
