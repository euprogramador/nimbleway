package br.com.aexo.nimbleway;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Invocation {

	private Long id;
	private String function;
	private Map<String, Object> options;
	private Object[] arguments;
	private Map<String, Object> payload;

	public Invocation(Long id, String function, Map<String, Object> options, Object[] arguments, Map<String, Object> payload) {
		this.id = id;
		this.function = function;
		this.options = options;
		this.arguments = arguments;
		this.payload = payload;

	}

	public static Invocation function(String function) {
		return new Invocation(null, function, Collections.emptyMap(), new Object[0], Collections.emptyMap());
	}

	public String getFunction() {
		return function;
	}

	public Map<String, Object> getOptions() {
		return Collections.unmodifiableMap(options);
	}

	public Object[] getArguments() {
		return arguments;
	}

	public Invocation option(String option, Object value) {
		HashMap<String, Object> options = new HashMap<String, Object>(this.options);
		options.put(option, value);
		return new Invocation(id, function, options, arguments, payload);
	}

	public Invocation args(Object... arguments) {
		return new Invocation(id, function, options, arguments, payload);
	}

	public Invocation payload(String key, Object value) {
		HashMap<String, Object> payload = new HashMap<String, Object>(this.payload);
		payload.put(key, value);
		return new Invocation(id, function, options, arguments, payload);
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public Invocation callId(Long callId) {
		return new Invocation(callId,function,options,arguments,payload);
	}

}
