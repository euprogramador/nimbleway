package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.Registration;
import br.com.aexo.nimbleway.WampError;


public class RegisterMessage extends DeferredWampMessage<Registration, WampError> {

	private Long id;
	private Deferred<Registration, WampError, Object> def;
	private Promise<Registration, WampError, Object> promise;
	private Registration registration;

	public RegisterMessage(Registration registration) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.registration = registration;
		this.def = new DeferredObject<Registration, WampError, Object>();
		this.promise = def.promise();
	}

	public Long getId() {
		return id;
	}

	public Promise<Registration, WampError, Object> getPromise() {
		return promise;
	}
	
	public Registration getRegistration() {
		return registration;
	}

	@Override
	protected Deferred<Registration, WampError, Object> getDefered() {
		return def;
	}

}
