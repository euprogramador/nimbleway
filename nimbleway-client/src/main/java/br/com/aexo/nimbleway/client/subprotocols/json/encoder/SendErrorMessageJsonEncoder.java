package br.com.aexo.nimbleway.client.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.aexo.nimbleway.client.messages.SendErrorMessage;
import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonEncoderMessage;

public class SendErrorMessageJsonEncoder implements JsonEncoderMessage<SendErrorMessage> {

	@Override
	public Object encode(SendErrorMessage msg) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(8);
			node.add(msg.getType().getId());
			node.add(msg.getRequestId());

			ObjectNode details = mapper.createObjectNode();

			msg.getResultError().getDetails().forEach((k, v) -> {
				details.set(k, mapper.valueToTree(v));
			});

			node.add(details);
			node.add(msg.getResultError().getError());
			node.add(mapper.valueToTree(msg.getResultError().getArguments()));
			node.add(mapper.valueToTree(msg.getResultError().getPayload()));

			StringWriter writer = new StringWriter();

			mapper.writeValue(writer, node);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isEncodeOf(ClientMessage type) {
		return type instanceof SendErrorMessage;
	}

}
