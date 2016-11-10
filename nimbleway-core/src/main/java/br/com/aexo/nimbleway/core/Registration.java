package br.com.aexo.nimbleway.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Registration {

	private Long id;
	private String name;
	private Map<String, Object> options;
	private Consumer<RegistrationCall> callback;

	public Registration(Long id, String name, Map<String, Object> options, Consumer<RegistrationCall> callback) {
		this.id = id;
		this.name = name;
		this.options = options;
		this.callback = callback;
	}

	public static Registration toName(String name) {
		return new Registration(null, name, Collections.emptyMap(), (c) -> {
		});
	}

	public Registration option(String key, Object value) {
		Map<String, Object> options = new HashMap<String, Object>(this.options);
		options.put(key, value);
		return new Registration(id, name, options, callback);
	}

	public Registration callback(Consumer<RegistrationCall> callback) {
		return new Registration(id, name, options, callback);
	}

	public Registration registrationId(Long id) {
		return new Registration(id, name, options, callback);
	}

	public String getName() {
		return name;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public Consumer<RegistrationCall> getCallback() {
		return callback;
	}

	public Long getId() {
		return id;
	}

}
