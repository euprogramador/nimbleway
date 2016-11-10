package br.com.aexo.nimbleway;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Subscription {

	private Long id;
	private String topic;
	private Map<String, Object> options = new HashMap<>();
	private Consumer<SubscriptionCall> callback;

	private Subscription(Long id, String topic, Map<String, Object> options, Consumer<SubscriptionCall> callback) {
		this.id = id;
		this.topic = topic;
		this.options = options;
		this.callback = callback;
	}

	public static Subscription toTopic(String topic) {
		return new Subscription(null, topic, Collections.emptyMap(), (c) -> {
		});
	}

	public Subscription option(String option, Object value) {
		HashMap<String, Object> options = new HashMap<String, Object>(this.options);
		options.put(option, value);
		return new Subscription(id, topic, options, callback);
	}

	public Subscription callback(Consumer<SubscriptionCall> callback) {
		return new Subscription(id, topic, options, callback);
	}

	public Long getId() {
		return id;
	}

	public Map<String, Object> getOptions() {
		return Collections.unmodifiableMap(options);
	}

	public String getTopic() {
		return topic;
	}

	public Consumer<SubscriptionCall> getCallback() {
		return callback;
	}

	public Subscription registrationId(Long registrationId) {
		return new Subscription(registrationId, topic, options, callback);
	}
}
