package nimbleway.messageformats.json;

import nimbleway.MessageFormat;
import nimbleway.WampMessage;
import nimbleway.messages.HelloMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Wamp2JsonHelloMessageFormat implements MessageFormat {

	private ObjectMapper objectMapper;

	public Wamp2JsonHelloMessageFormat(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public WampMessage decode(Object data) {
		try {
			ArrayNode value;
			if (data instanceof ArrayNode) {
				value = (ArrayNode) data;
			} else {
				value = objectMapper.readValue(data.toString(), ArrayNode.class);
			}

			String realm = value.get(1).textValue();
			return new HelloMessage(realm);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
