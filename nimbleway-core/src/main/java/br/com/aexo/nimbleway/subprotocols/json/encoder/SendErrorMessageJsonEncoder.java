package br.com.aexo.nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.SendErrorMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
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
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof SendErrorMessage;
	}

}
