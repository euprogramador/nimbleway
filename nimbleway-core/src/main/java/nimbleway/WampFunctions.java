package nimbleway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class WampFunctions {

	Map<Long,WampFunction> waitRegistration = new HashMap<Long, WampFunction>();
	Map<Long,WampFunction> registredFunction = new HashMap<Long, WampFunction>();
	
	public void waitRegistration(WampFunction function){
		waitRegistration.put(function.getIdWaitRegister(), function);
	}

	public void registryFunction(Long idRequest, Long idRegistration) {
		WampFunction function = waitRegistration.get(idRequest);
		waitRegistration.remove(idRequest);
		registredFunction.put(idRegistration, function);
	}

	public void callRegistredFn(Long idFunctionRegistred, WampInvocation invocation) {
		registredFunction.get(idFunctionRegistred).getFn().accept(invocation);
	}

}
