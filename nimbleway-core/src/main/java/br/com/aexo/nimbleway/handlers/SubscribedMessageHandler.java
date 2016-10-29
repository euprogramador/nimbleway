package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.SubscribedMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampSubscriptions;

/**
 * handle subscribed recording function in session
 * 
 * @author carlosr
 *
 */
@Component
public class SubscribedMessageHandler implements MessageHandler<SubscribedMessage> {
	private WampSubscriptions subscriptions;

	public SubscribedMessageHandler(WampSubscriptions subscriptions) {
		this.subscriptions = subscriptions;
	}

	@Override
	public void handle(SubscribedMessage message) {
		subscriptions.registryFunction(message.getIdRequest(), message.getIdRegistration());
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof SubscribedMessage;
	}
}
