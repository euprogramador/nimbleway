package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.Subscription;
import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.DefaultClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.SubscribeMessage;
import br.com.aexo.nimbleway.messages.SubscribedMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle subscribed recording function in session
 * 
 * @author carlosr
 *
 */
@Component
public class SubscribedMessageHandler implements MessageHandler<SubscribedMessage> {
	private WaitBrokerCommunication wait;

	public SubscribedMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(SubscribedMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		SubscribeMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(TypeMessage.SUBSCRIBE,message.getRequestId()));
		Subscription subscription = requestMessage.getSubscription().registrationId(message.getRegistrationId());
		rawSession.save(subscription);
		requestMessage.resolve(subscription);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof SubscribedMessage;
	}
}
