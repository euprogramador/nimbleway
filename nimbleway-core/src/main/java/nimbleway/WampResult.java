package nimbleway;

import nimbleway.messages.ResultMessage;

import com.fasterxml.jackson.databind.ObjectMapper;


public class WampResult {

	private ResultMessage message;

	public WampResult(ResultMessage message) {
		this.message = message;

	}

	public <T> T as(Class<T> tipo) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(message.getResult(), tipo);
	}

}
