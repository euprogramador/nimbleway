package br.com.aexo.nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.SubscribeMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class SubscribeMessageJsonEncoder implements JsonEncoderMessage<SubscribeMessage> {

	@Override
	public Object encode(SubscribeMessage msg) {

		ObjectMapper mapper = new ObjectMapper();

		ArrayNode node = mapper.createArrayNode();
		node.add(32);

		node.add(msg.getId()); 

		ObjectNode options = mapper.createObjectNode();

		msg.getSubscription().getOptions().forEach((k,v)-> {
			options.set(k,mapper.valueToTree(v));
		});
		
		node.add(options);
		node.add(msg.getSubscription().getTopic());

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
		return type instanceof SubscribeMessage;
	}

}
