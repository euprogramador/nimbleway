package br.com.aexo.nimbleway.client.interaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultError {

	private Map<String, Object> details;
	private Object[] arguments;
	private Map<String, Object> payload;
	private String error;

	private ResultError(String error, Map<String, Object> details, Object[] arguments, Map<String, Object> payload) {
		this.error = error;
		this.details = details;
		this.arguments = arguments;
		this.payload = payload;

	}
	
	public String getError() {
		return error;
	}
	
	
	public static ResultError error(String error){
		return new ResultError(error,Collections.emptyMap(),new Object[0],Collections.emptyMap());
	}

	public Map<String, Object> getDetails() {
		return Collections.unmodifiableMap(details);
	}

	public Object[] getArguments() {
		return arguments;
	}

	public ResultError detail(String option, Object value) {
		HashMap<String, Object> details = new HashMap<String, Object>(this.details);
		details.put(option, value);
		return new ResultError(error, details, arguments, payload);
	}

	public ResultError args(Object... arguments) {
		return new ResultError(error, details, arguments, payload);
	}

	public ResultError payload(String key, Object value) {
		HashMap<String, Object> payload = new HashMap<String, Object>(this.payload);
		payload.put(key, value);
		return new ResultError(error, details, arguments, payload);
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

}
