package br.com.aexo.nimbleway;

import br.com.aexo.nimbleway.messages.ResultMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * represent result of procedure call
 * 
 * @author carlosr
 *
 */
public class WampResult {

	private ResultMessage message;
/**
 * create a wamp result
 * @param message
 */
	public WampResult(ResultMessage message) {
		this.message = message;

	}

	// TODO melhorar para processar o object ampper customizado
	public <T> T as(Class<T> tipo) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(message.getResult(), tipo);
	}

}
