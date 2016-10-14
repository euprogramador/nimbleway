package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerContext {

	private Map<Enum<?>, Object> context = new HashMap<>();

	public void set(Enum<?> key, Object value) {
		context.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Enum<?> key) {
		return (T) context.get(key);
	}

}
