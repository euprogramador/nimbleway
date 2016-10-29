package br.com.aexo.nimbleway;

import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.YieldMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * represent a wamp call received from router in another client
 * 
 * @author carlosr
 *
 */
public class WampEvent {

	private InvocationMessage message;
	private WampTransport transport;

	/**
	 * create a wamp invocation
	 * 
	 * @param message
	 * @param transport
	 */
	public WampEvent(InvocationMessage message, WampTransport transport) {
		this.message = message;
		this.transport = transport;
	}

	public void sendResult(Object reply) {

		// TODO melhorar a tratativa do retorno das mensagens usando um
		// objectmapper passado na configuração do client, permitindo assim que
		// o processo de serialização seja customizado

		InvocationMessage inReplyTo = message;
		YieldMessage yieldMessage = new YieldMessage(inReplyTo, reply);
		transport.write(yieldMessage);
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
