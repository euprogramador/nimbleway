package br.com.aexo.nimbleway.client.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.messages.UnregisterMessage;
import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonEncoderMessage;

public class UnregisterMessageJsonEncoder  implements JsonEncoderMessage<UnregisterMessage> {

	@Override
	public Object encode(UnregisterMessage msg) {
		 
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayNode node = mapper.createArrayNode();
		node.add(66);

		node.add(msg.getId()); 
		node.add(msg.getRegistration().getId());
		
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
		return type instanceof UnregisterMessage;
	}

}

