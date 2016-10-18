package nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import nimbleway.messages.HelloMessage;
import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
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

	@Override
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof HelloMessage;
	}

}
