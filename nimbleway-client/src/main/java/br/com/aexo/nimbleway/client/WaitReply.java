package br.com.aexo.nimbleway.client;

import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.core.WampError;
import br.com.aexo.nimbleway.core.messages.WampMessage;

public class WaitReply<A extends WampMessage, T> {

	private A message;
	private DeferredObject<T, WampError, Object> deferred;

	public WaitReply(A message) {
		this.message = message;
		this.deferred = new DeferredObject<T, WampError, Object>();
	}

	public A getMessage() {
		return message;
	}

	public void resolve(T resolved) {
		deferred.resolve(resolved);
	}

	public void reject(WampError rejected) {
		deferred.reject(rejected);
	}

	// TODO implementar o progressive call results por aqui
	// public void progress()

	public Promise<T, WampError, Object> getPromise() {
		return deferred.promise();
	}

}
