package br.com.aexo.nimbleway;

import br.com.aexo.nimbleway.messages.EventMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * represent a wamp call received from router in another client
 * 
 * @author carlosr
 *
 */
public class WampInvocation {

	private EventMessage message;

	/**
	 * create a wamp invocation
	 * 
	 * @param message
	 * @param transport
	 */
	public WampInvocation(EventMessage message) {
		this.message = message;
	}

	/**
	 * read positional parameter
	 * 
	 * @param position
	 * @return
	 */
	public WampParam params(int position) {
		return new WampParam(message.getParams().get(position));
	}

	// TODO implementar o recebimento de parametros nomeados.

	public class WampParam {

		private JsonNode jsonNode;

		public WampParam(JsonNode jsonNode) {
			this.jsonNode = jsonNode;
		}

		// TODO usar um object mapper da session pois deve permitir que o
		// cliente possa customizar o objectmapper do retorno da chamada.
		public <T> T as(Class<T> tipo) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(jsonNode, tipo);
		}

	}

}
