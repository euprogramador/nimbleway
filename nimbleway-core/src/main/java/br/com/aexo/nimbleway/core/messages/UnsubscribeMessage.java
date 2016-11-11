package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.Subscription;

public class UnsubscribeMessage implements WampMessage {

	private long id;
	private Subscription subscription;

	public UnsubscribeMessage(Subscription subscription) {
		this.subscription = subscription;
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
	}

	public long getId() {
		return id;
	}

	public Subscription getSubscription() {
		return subscription;
	}

}
