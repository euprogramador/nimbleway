package br.com.aexo.nimbleway.core.messages;

import org.jdeferred.Deferred;

public abstract class DeferredWampMessage<SUCCESS,ERROR> implements WampMessage {

	protected abstract Deferred<SUCCESS,ERROR,Object> getDefered();
	
	public void resolve(SUCCESS resolved){
		getDefered().resolve(resolved);
	}
	
	public void reject(ERROR rejection){
		getDefered().reject(rejection);
	}
	
}
