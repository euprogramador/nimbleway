package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.WampError;

public class UnsubscribeMessage extends DeferredWampMessage<Subscription, WampError> {

	private long id;
	private DeferredObject<Subscription, WampError, Object> def;
	private Promise<Subscription, WampError, Object> promise;
	private Subscription subscription;

	public UnsubscribeMessage(Subscription subscription) {
		this.subscription = subscription;
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.def = new DeferredObject<Subscription, WampError, Object>();
		this.promise = def.promise();
	}

	public long getId() {
		return id;
	}

	public Subscription getSubscription() {
		return subscription;
	}


	public Promise<Subscription, WampError, Object> getPromise() {
		return promise;
	}

	protected Deferred<Subscription, WampError, Object> getDefered() {
		return def;
	}

}
