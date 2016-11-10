package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.Subscription;
import br.com.aexo.nimbleway.WampError;

/**
 * represent wamp subscribe message solicitation
 * 
 * @author carlosr
 *
 */
public class SubscribeMessage extends DeferredWampMessage<Subscription, WampError> {

	private Long id;
	private Subscription subscription;

	private Deferred<Subscription, WampError, Object> def;
	private Promise<Subscription, WampError, Object> promise;

	public SubscribeMessage(Subscription subscription) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.subscription = subscription;
		this.def = new DeferredObject<Subscription, WampError, Object>();
		this.promise = def.promise();
	}

	public Long getId() {
		return id;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public Promise<Subscription, WampError, Object> getPromise() {
		return promise;
	}

	@Override
	protected Deferred<Subscription, WampError, Object> getDefered() {
		return def;
	}

}
