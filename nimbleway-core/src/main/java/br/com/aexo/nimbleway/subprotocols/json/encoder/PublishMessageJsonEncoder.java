package br.com.aexo.nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.PublishMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.RawValue;

@Component
public class PublishMessageJsonEncoder implements JsonEncoderMessage<PublishMessage> {

	@Override
	public Object encode(PublishMessage msg) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(16);
			node.add(msg.getId());

			node.add(mapper.createObjectNode()); // options
			node.add(msg.getTopic());

//			// serializa os parametros como uma array e envia
//			node.addRawValue(new RawValue(mapper.writeValueAsString(msg
//					.getParams())));

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
