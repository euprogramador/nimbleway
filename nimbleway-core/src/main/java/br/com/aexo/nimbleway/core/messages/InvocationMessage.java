package br.com.aexo.nimbleway.core.messages;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class InvocationMessage implements WampMessage {

	private final Long requestId;
	private final Long functionId;
	private Map<String, Object> details;
	private ArrayNode params;
	private JsonNode payload;

	public InvocationMessage(Long requestId, Long functionId, ArrayNode params, Map<String, Object> details, JsonNode payload) {
		this.requestId = requestId;
		this.functionId = functionId;
		this.params = params;
		this.details = details;
		this.payload = payload;
	}

	public Long getRequestId() {
		return requestId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public ArrayNode getParams() {
		return params;
	}

	public JsonNode getPayload() {
		return payload;
	}
}
