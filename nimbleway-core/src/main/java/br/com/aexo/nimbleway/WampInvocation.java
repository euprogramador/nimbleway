package br.com.aexo.nimbleway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.YieldMessage;

public class WampInvocation {

	private InvocationMessage message;
	private WampTransport transport;

	public WampInvocation(InvocationMessage message, WampTransport transport) {
		this.message = message;
		this.transport = transport;
	}

	public void sendResult(Object reply) {
		InvocationMessage inReplyTo = message;
		YieldMessage yieldMessage = new YieldMessage(inReplyTo,reply);
		transport.write(yieldMessage);
	}

	public WampParam params(int i) {
		return new WampParam(message.getParams().get(i));
	}

	public class WampParam {

		private JsonNode jsonNode;

		public WampParam(JsonNode jsonNode) {
			this.jsonNode = jsonNode;
		}

		public <T> T as(Class<T> tipo) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(jsonNode, tipo);
		}

	}

}
