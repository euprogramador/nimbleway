package br.com.aexo.nimbleway.core.messages;

import br.com.aexo.nimbleway.core.Subscription;

public class UnsubscribeMessage implements WampMessage {

	private long id;
	private Subscription subscription;

	public UnsubscribeMessage(Long id,Subscription subscription) {
		this.subscription = subscription;
		this.id = id; 
	}

	public long getId() {
		return id;
	}

	public Subscription getSubscription() {
		return subscription;
	}

}
