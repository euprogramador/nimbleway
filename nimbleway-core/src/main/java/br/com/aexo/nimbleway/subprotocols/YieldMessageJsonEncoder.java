package br.com.aexo.nimbleway.subprotocols;

import java.io.IOException;
import java.io.StringWriter;

import br.com.aexo.nimbleway.EncoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.YieldMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class YieldMessageJsonEncoder implements EncoderMessage {

	@Override
	public Object encode(WampMessage wampMessage) {
		try {

			YieldMessage msg = (YieldMessage) wampMessage;

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

}