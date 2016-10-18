package nimbleway.subprotocols.json.encoder;

import java.io.IOException;
import java.io.StringWriter;

import nimbleway.messages.CallMessage;
import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.json.JsonEncoderMessage;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.RawValue;

@Component
public class CallMessageJsonEncoder implements JsonEncoderMessage<CallMessage> {

	@Override
	public Object encode(CallMessage msg) {
		try {
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

	@Override
	public boolean isEncodeOf(WampMessage type) {
		return type instanceof CallMessage;
	}

}
