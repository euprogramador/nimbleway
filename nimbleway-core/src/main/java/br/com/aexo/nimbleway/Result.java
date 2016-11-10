package br.com.aexo.nimbleway;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Result {

	private Map<String, Object> options;
	private Object[] arguments;
	private Map<String, Object> payload;

	private Result(Map<String, Object> options, Object[] arguments, Map<String, Object> payload) {
		this.options = options;
		this.arguments = arguments;
		this.payload = payload;
	}

	public static Result create() {
		return new Result(Collections.emptyMap(), new Object[0], Collections.emptyMap());
	}

	public Map<String, Object> getOptions() {
		return Collections.unmodifiableMap(options);
	}

	public Object[] getArguments() {
		return arguments;
	}

	public Result option(String option, Object value) {
		HashMap<String, Object> options = new HashMap<String, Object>(this.options);
		options.put(option, value);
		return new Result(options, arguments, payload);
	}

	public Result args(Object... arguments) {
		return new Result(options, arguments, payload);
	}

	public Result payload(String key, Object value) {
		HashMap<String, Object> payload = new HashMap<String, Object>(this.payload);
		payload.put(key, value);
		return new Result(options, arguments, payload);
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

}
