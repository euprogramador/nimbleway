package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.Subscription;

public class UnsubscribeMessage implements ClientMessage {

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
