package br.com.aexo.nimbleway.client.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.aexo.nimbleway.client.messages.CallMessage;
import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonEncoderMessage;

public class CallMessageJsonEncoder implements JsonEncoderMessage<CallMessage> {

	@Override
	public Object encode(CallMessage msg) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(48);
			node.add(msg.getId());

			ObjectNode options = mapper.createObjectNode();

			msg.getInvocation().getOptions().forEach((k,v)-> {
				options.set(k,mapper.valueToTree(v));
			});
			
			node.add(options);
			node.add(msg.getInvocation().getFunction());

			node.add(mapper.valueToTree(msg.getInvocation().getArguments()));
			node.add(mapper.valueToTree(msg.getInvocation().getPayload()));

			StringWriter writer = new StringWriter();

			mapper.writeValue(writer, node);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isEncodeOf(ClientMessage type) {
		return type instanceof CallMessage;
	}

}
