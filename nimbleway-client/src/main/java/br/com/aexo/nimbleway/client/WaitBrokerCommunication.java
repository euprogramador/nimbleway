package br.com.aexo.nimbleway.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampError;
import br.com.aexo.nimbleway.messages.DeferredWampMessage;

@Component
public class WaitBrokerCommunication {

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
