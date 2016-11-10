package br.com.aexo.nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.messages.YieldMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonEncoderMessage;

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

			node.add(msg.getInReplyTo().getRequestId());

			ObjectNode options = mapper.createObjectNode();

			msg.getReply().getOptions().forEach((k,v)-> {
				options.set(k,mapper.valueToTree(v));
			});
			
			node.add(options);
			
			node.add(mapper.valueToTree(msg.getReply().getArguments()));
			node.add(mapper.valueToTree(msg.getReply().getPayload()));
			

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