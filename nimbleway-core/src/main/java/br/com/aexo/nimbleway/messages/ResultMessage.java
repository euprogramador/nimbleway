package br.com.aexo.nimbleway.messages;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * represent wamp result message
 * 
 * @author carlosr
 *
 */
public class ResultMessage implements WampMessage {

	private final Long callId;
	private final Map<String, Object> details;
	private final ArrayNode params;
	private final JsonNode payload;

	public ResultMessage(Long callId, Map<String, Object> details, ArrayNode params, JsonNode payload) {
		super();
		this.callId = callId;
		this.details = details;
		this.params = params;
		this.payload = payload;
	}

	public Long getCallId() {
		return callId;
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
