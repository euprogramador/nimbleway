package nimbleway.messageformats.json;

import nimbleway.MessageFormat;
import nimbleway.WampMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Wamp2JsonWelcomeMessageFormat implements MessageFormat {

	private ObjectMapper objectMapper;

	public Wamp2JsonWelcomeMessageFormat(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public WampMessage decode(Object data) {
		return null;
	}

}
