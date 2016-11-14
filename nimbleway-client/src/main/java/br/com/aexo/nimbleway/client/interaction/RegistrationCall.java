package br.com.aexo.nimbleway.client.interaction;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aexo.nimbleway.client.connection.ClientTransport;
import br.com.aexo.nimbleway.client.messages.InvocationMessage;
import br.com.aexo.nimbleway.client.messages.SendErrorMessage;
import br.com.aexo.nimbleway.client.messages.YieldMessage;

public class RegistrationCall {

	private InvocationMessage message;
	private ClientTransport transport;

	public RegistrationCall(InvocationMessage message, ClientTransport transport) {
		this.message = message;
		this.transport = transport;
	}

	public WampParam payload(String key) {
		return new WampParam(message.getPayload().get(key));
	}

	public WampParam params(int position) {
		return new WampParam(message.getParams().get(position));
	}

	public Map<String, Object> details() {
		return message.getDetails();
	}

	public void replyWith(Result result) {
		YieldMessage msg = new YieldMessage(message, result);
		transport.write(msg);
	}

	public void replyWith(ResultError resultError) {
		SendErrorMessage error = new SendErrorMessage(MessageType.INVOCATION, message.getRequestId(), resultError);
		transport.write(error);
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

	public WampParam[] params() {
		WampParam[] params = new WampParam[message.getParams().size()];
		AtomicInteger counter = new AtomicInteger();
		message.getParams().forEach((p) -> {
			params[counter.getAndIncrement()] = new WampParam(p);
		});
		return params;
	}

}