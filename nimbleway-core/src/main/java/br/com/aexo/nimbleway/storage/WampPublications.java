package br.com.aexo.nimbleway.storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampResult;

@Component
public class WampPublications {

	private Map<Long, WampCall> calls = new HashMap<>();

	public void registerCall(WampCall call) {
		calls.put(call.getId(), call);
	}

	public void resolve(Long idCall, WampResult wampResult) {
		WampCall call = calls.get(idCall);
		calls.remove(idCall);
		call.getDeferred().resolve(wampResult);
	}

	public Map<Long, WampCall> getCalls() {
		return Collections.unmodifiableMap(calls);
	}
}
