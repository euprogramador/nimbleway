package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.Subscription;

/**
 * represent wamp subscribe message solicitation
 * 
 * @author carlosr
 *
 */
public class SubscribeMessage implements ClientMessage  {

	private Long id;
	private Subscription subscription;

	public SubscribeMessage(Long id,Subscription subscription) {
		this.id = id; 
		this.subscription = subscription;
	}

	public Long getId() {
		return id;
	}

	public Subscription getSubscription() {
		return subscription;
	}



}
