package br.com.aexo.nimbleway.subprotocols;

import java.io.IOException;
import java.io.StringWriter;

import br.com.aexo.nimbleway.EncoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.HelloMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HelloMessageJsonEncoder implements EncoderMessage {

	@Override
	public Object encode(WampMessage wampMessage) {
		
		HelloMessage msg = (HelloMessage) wampMessage;
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayNode node = mapper.createArrayNode();
		node.add(1);
		node.add(msg.getReaml());

		ObjectNode roles = mapper.createObjectNode();
		
		ObjectNode internalRoles = mapper.createObjectNode();
		roles.set("roles",internalRoles );
		internalRoles.set("publisher", mapper.createObjectNode());
		internalRoles.set("subscriber", mapper.createObjectNode());
		
		node.add(roles);
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, node);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return writer.toString();
	}

}
