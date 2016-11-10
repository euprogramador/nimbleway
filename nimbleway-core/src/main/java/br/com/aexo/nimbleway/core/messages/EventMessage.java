package br.com.aexo.nimbleway.core.messages;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class EventMessage implements WampMessage {

	private Long publicationId;
	private Map<String, Object> options;
	private ArrayNode params;
	private JsonNode payload;
	private Long subscriptionId;

	public EventMessage(Long subscriptionId, Long publicationId, Map<String, Object> options, ArrayNode params, JsonNode payload) {
		this.subscriptionId = subscriptionId;
		this.publicationId = publicationId;
		this.options = options;
		this.params = params;
		this.payload = payload;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public ArrayNode getParams() {
		return params;
	}

	public JsonNode getPayload() {
		return payload;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

}
