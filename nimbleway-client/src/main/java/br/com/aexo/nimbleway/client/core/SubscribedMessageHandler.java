package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.messages.SubscribeMessage;
import br.com.aexo.nimbleway.core.messages.SubscribedMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle subscribed recording function in session
 * 
 * @author carlosr
 *
 */
@Component
class SubscribedMessageHandler implements MessageHandler<SubscribedMessage> {
	private WaitBrokerCommunication wait;

	public SubscribedMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(SubscribedMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		SubscribeMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(MessageType.SUBSCRIBE,message.getRequestId()));
		Subscription subscription = requestMessage.getSubscription().registrationId(message.getRegistrationId());
		rawSession.save(subscription);
		requestMessage.resolve(subscription);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof SubscribedMessage;
	}
}
