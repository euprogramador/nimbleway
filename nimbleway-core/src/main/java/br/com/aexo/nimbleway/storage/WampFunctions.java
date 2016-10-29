package br.com.aexo.nimbleway.storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampInvocationResult;

/**
 * storage of functions wait registration and registred functions
 * 
 * @author carlosr
 *
 */
@Component
public class WampFunctions {

	private Map<Long, WampFunction> waitRegistration = new HashMap<Long, WampFunction>();
	private Map<Long, WampFunction> registredFunction = new HashMap<Long, WampFunction>();

	public void waitRegistration(WampFunction function) {
		waitRegistration.put(function.getIdWaitRegister(), function);
	}

	public void registryFunction(Long idRequest, Long idRegistration) {
		WampFunction function = waitRegistration.get(idRequest);
		waitRegistration.remove(idRequest);
		registredFunction.put(idRegistration, function);
	}

	public void callRegistredFn(Long idFunctionRegistred, WampInvocationResult invocation) {
		registredFunction.get(idFunctionRegistred).getFn().accept(invocation);
	}

	public Map<Long, WampFunction> getWaitRegistration() {
		return Collections.unmodifiableMap(waitRegistration);
	}
	
	public Map<Long, WampFunction> getRegistredFunction() {
		return Collections.unmodifiableMap(registredFunction);
	}
	
}
