package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.WampError;

public class UnregisterMessage extends DeferredWampMessage<Registration, WampError> {

	private Long id;
	private Deferred<Registration, WampError, Object> def;
	private Promise<Registration, WampError, Object> promise;
	private Registration registration;

	public UnregisterMessage(Registration registration) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.registration = registration;
		this.def = new DeferredObject<Registration, WampError, Object>();
		this.promise = def.promise();
	}

	public Long getId() {
		return id;
	}

	public Registration getRegistration() {
		return registration;
	}

	public Promise<Registration, WampError, Object> getPromise() {
		return promise;
	}

	@Override
	protected Deferred<Registration, WampError, Object> getDefered() {
		return def;
	}

}