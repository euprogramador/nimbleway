package br.com.aexo.nimbleway.core;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import br.com.aexo.nimbleway.core.messages.ResultMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResultCall {
	private ResultMessage message;

	public ResultCall(ResultMessage message) {
		this.message = message;
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
