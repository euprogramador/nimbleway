package nimbleway.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class WampSessionContext {

	private Map<Enum<?>, Object> context = new HashMap<>();

	public void set(Enum<?> key, Object value) {
		context.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Enum<?> key) {
		return (T) context.get(key);
	}

}
