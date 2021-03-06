package br.com.aexo.nimbleway.client.messages;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.client.interaction.MessageType;

public class ReplyErrorMessage implements ClientMessage {

	private final MessageType type;
	private final Long requestId;
	private final Map<String, Object> details;
	private final String error;
	private ArrayNode params;
	private JsonNode payload;

	public ReplyErrorMessage(MessageType type, Long requestId, Map<String, Object> details, String error, ArrayNode params, JsonNode payload) {
		super();
		this.type = type;
		this.requestId = requestId;
		this.details = details;
		this.error = error;
		this.params = params;
		this.payload = payload;
	}

	public MessageType getType() {
		return type;
	}

	public Long getRequestId() {
		return requestId;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public String getError() {
		return error;
	}

	public JsonNode getPayload() {
		return payload;
	}

	public ArrayNode getParams() {
		return params;
	}

}
