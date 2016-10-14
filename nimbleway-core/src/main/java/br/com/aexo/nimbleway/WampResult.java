package br.com.aexo.nimbleway;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aexo.nimbleway.messages.ResultMessage;

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
