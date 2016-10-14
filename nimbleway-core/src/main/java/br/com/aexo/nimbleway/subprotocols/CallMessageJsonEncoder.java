package br.com.aexo.nimbleway.subprotocols;

import java.io.IOException;
import java.io.StringWriter;

import br.com.aexo.nimbleway.EncoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.CallMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.RawValue;

public class CallMessageJsonEncoder implements EncoderMessage {

	@Override
	public Object encode(WampMessage wampMessage) {
		try {
			CallMessage msg = (CallMessage) wampMessage;

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode node = mapper.createArrayNode();
			node.add(48);
			node.add(msg.getId());

			node.add(mapper.createObjectNode()); // options
			node.add(msg.getFnName());
			
			// serializa os parametros como uma array e envia
			node.addRawValue(new RawValue(mapper.writeValueAsString(msg
					.getParams())));

			StringWriter writer = new StringWriter();

			mapper.writeValue(writer, node);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
