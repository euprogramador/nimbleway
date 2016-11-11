package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.Subscription;

/**
 * represent wamp subscribe message solicitation
 * 
 * @author carlosr
 *
 */
public class SubscribeMessage implements WampMessage  {

	private Long id;
	private Subscription subscription;

	public SubscribeMessage(Subscription subscription) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.subscription = subscription;
	}

	public Long getId() {
		return id;
	}

	public Subscription getSubscription() {
		return subscription;
	}



}
