package nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import nimbleway.messages.RegisterMessage;
import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.json.JsonEncoderMessage;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class RegisterMessageJsonEncoder  implements JsonEncoderMessage<RegisterMessage> {

	@Override
	public Object encode(RegisterMessage msg) {
		 
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayNode node = mapper.createArrayNode();
		node.add(64);
		
		node.add(msg.getId()); // um numero randomico de 8 digitos
		
		ObjectNode options = mapper.createObjectNode();
		node.add(options);
		node.add(msg.getName());
		
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
		return type instanceof RegisterMessage;
	}

}

