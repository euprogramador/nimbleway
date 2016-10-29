package br.com.aexo.nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.messages.YieldMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonEncoderMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class YieldMessageJsonEncoder implements JsonEncoderMessage<YieldMessage> {


	@Override
	public Object encode(YieldMessage msg) {
		try {


			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(70);

			node.add(msg.getInReplyTo().getIdRequest());

			ObjectNode options = mapper.createObjectNode();
			node.add(options);
			ArrayNode result = mapper.createArrayNode();
			JsonNode resultCall = mapper.valueToTree(msg.getReply());
			result.add(resultCall);
			node.add(result);

			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, node);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof YieldMessage;
	}

}