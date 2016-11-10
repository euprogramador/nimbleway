package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.Subscription;
import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.DefaultClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.messages.UnsubscribedMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

@Component
public class UnsubscribedMessageHandler implements MessageHandler<UnsubscribedMessage> {
	private WaitBrokerCommunication wait;

	public UnsubscribedMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(UnsubscribedMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		UnsubscribeMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(TypeMessage.UNSUBSCRIBE, message.getRequestId()));
		
		Subscription subscription = requestMessage.getSubscription();
		rawSession.remove(subscription);
		requestMessage.resolve(subscription);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof UnsubscribedMessage;
	}
}
