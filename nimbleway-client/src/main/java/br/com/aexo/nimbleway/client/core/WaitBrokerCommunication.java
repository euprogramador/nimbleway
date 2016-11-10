package br.com.aexo.nimbleway.client.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.core.WampError;
import br.com.aexo.nimbleway.core.messages.DeferredWampMessage;

@Component
class WaitBrokerCommunication {

	private Map<BrokerCommunicationKey, DeferredWampMessage<? extends Object,WampError>> wait = new HashMap<BrokerCommunicationKey, DeferredWampMessage<? extends Object, WampError>>();

	public void wait(BrokerCommunicationKey key, DeferredWampMessage<? extends Object,WampError> o) {
		wait.put(key, o);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T retrieve(BrokerCommunicationKey id){
		DeferredWampMessage<? extends Object,WampError> o = wait.get(id);
		wait.remove(id);
		return (T) o; 
	}

}
