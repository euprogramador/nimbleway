package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.core.Invocation;
import br.com.aexo.nimbleway.core.ResultCall;
import br.com.aexo.nimbleway.core.WampError;

public class CallMessage extends DeferredWampMessage<ResultCall, WampError> {

	private Long id;
	private Deferred<ResultCall, WampError, Object> def;
	private Promise<ResultCall, WampError, Object> promise;
	private Invocation invocation;

	public CallMessage(Invocation invocation) {
		this.invocation = invocation;
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.def = new DeferredObject<ResultCall, WampError, Object>();
		this.promise = def.promise();
	}

	public Long getId() {
		return id;
	}

	public Invocation getInvocation() {
		return invocation;
	}

	public Promise<ResultCall, WampError, Object> getPromise() {
		return promise;
	}

	@Override
	protected Deferred<ResultCall, WampError, Object> getDefered() {
		return def;
	}

}
