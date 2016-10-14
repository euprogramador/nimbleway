package br.com.aexo.nimbleway.subprotocols;

import java.io.IOException;
import java.io.StringWriter;

import br.com.aexo.nimbleway.EncoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.RegisterMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RegisterMessageJsonEncoder  implements EncoderMessage {

	@Override
	public Object encode(WampMessage wampMessage) {
		
		RegisterMessage msg = (RegisterMessage) wampMessage;
		
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

}

