package br.com.aexo.nimbleway;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Publication {

	private Long id;
	private String topic;
	private Map<String, Object> options;
	private Object[] arguments;
	private Map<String, Object> payload;

	public Publication(Long id, String topic, Map<String, Object> options, Object[] arguments, Map<String, Object> payload) {
		this.id = id;
		this.topic = topic;
		this.options = options;
		this.arguments = arguments;
		this.payload = payload;

	}

	public static Publication toTopic(String topic) {
		return new Publication(null, topic, Collections.emptyMap(), new Object[0], Collections.emptyMap());
	}

	public String getTopic() {
		return topic;
	}

	public Map<String, Object> getOptions() {
		return Collections.unmodifiableMap(options);
	}

	public Object[] getArguments() {
		return arguments;
	}

	public Publication option(String option, Object value) {
		HashMap<String, Object> options = new HashMap<String, Object>(this.options);
		options.put(option, value);
		return new Publication(id, topic, options, arguments, payload);
	}

	public Publication args(Object... arguments) {
		return new Publication(id, topic, options, arguments, payload);
	}

	public Publication payload(String key, Object value) {
		HashMap<String, Object> payload = new HashMap<String, Object>(this.payload);
		payload.put(key, value);
		return new Publication(id, topic, options, arguments, payload);
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public Publication publicationId(Long publicationId) {
		return new Publication(publicationId,topic,options,arguments,payload);
	}

}
