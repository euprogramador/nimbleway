package br.com.aexo.nimbleway.core.messages;

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
