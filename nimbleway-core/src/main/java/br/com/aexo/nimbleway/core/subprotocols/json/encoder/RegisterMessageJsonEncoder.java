package br.com.aexo.nimbleway.core.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.core.messages.RegisterMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
class RegisterMessageJsonEncoder  implements JsonEncoderMessage<RegisterMessage> {

	@Override
	public Object encode(RegisterMessage msg) {
		 
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayNode node = mapper.createArrayNode();
		node.add(64);
		
		node.add(msg.getId()); 
		ObjectNode options = mapper.createObjectNode();

		msg.getRegistration().getOptions().forEach((k,v)-> {
			options.set(k,mapper.valueToTree(v));
		});
		
		node.add(options);
		node.add(msg.getRegistration().getName());
		
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

