package br.com.aexo.nimbleway.core.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.aexo.nimbleway.core.messages.PublishMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonEncoderMessage;

public class PublishMessageJsonEncoder implements JsonEncoderMessage<PublishMessage> {

	@Override
	public Object encode(PublishMessage msg) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(16);
			node.add(msg.getId());

			ObjectNode options = mapper.createObjectNode();

			msg.getPublication().getOptions().forEach((k,v)-> {
				options.set(k,mapper.valueToTree(v));
			});
			
			node.add(options);
			node.add(msg.getPublication().getTopic());
			
			node.add(mapper.valueToTree(msg.getPublication().getArguments()));
			node.add(mapper.valueToTree(msg.getPublication().getPayload()));

			StringWriter writer = new StringWriter();

			mapper.writeValue(writer, node);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof PublishMessage;
	}

}
