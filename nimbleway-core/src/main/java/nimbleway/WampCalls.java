package nimbleway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class WampCalls {

	private Map<Long, WampCall> calls = new HashMap<>();
	
	public void registerCall(WampCall call) {
		calls.put(call.getId(), call);
	}

	public void resolve(Long idCall, WampResult wampResult) {
		calls.get(idCall).getDeferred().resolve(wampResult);
	}

}
