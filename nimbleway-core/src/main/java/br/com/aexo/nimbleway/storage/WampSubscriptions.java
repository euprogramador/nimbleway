package br.com.aexo.nimbleway.storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampInvocation;

/**
 * storage of functions wait registration and registred functions
 * 
 * @author carlosr
 *
 */
@Component
public class WampSubscriptions {

	private Map<Long, WampFunction<WampInvocation>> waitRegistration = new HashMap<Long, WampFunction<WampInvocation>>();
	private Map<Long, WampFunction<WampInvocation>> subscribedFunctions = new HashMap<Long, WampFunction<WampInvocation>>();

	public void waitRegistration(WampFunction<WampInvocation> function) {
		waitRegistration.put(function.getIdWaitRegister(), function);
	}

	public void registryFunction(Long idRequest, Long idRegistration) {
		WampFunction<WampInvocation> function = waitRegistration.get(idRequest);
		waitRegistration.remove(idRequest);
		subscribedFunctions.put(idRegistration, function);
	}

	public void callRegistredFn(Long idFunctionRegistred, WampInvocation invocation) {
		subscribedFunctions.get(idFunctionRegistred).getFn().accept(invocation);
	}

	public Map<Long, WampFunction<WampInvocation>> getWaitRegistration() {
		return Collections.unmodifiableMap(waitRegistration);
	}
	
	public Map<Long, WampFunction<WampInvocation>> getRegistredFunction() {
		return Collections.unmodifiableMap(subscribedFunctions);
	}
	
}
