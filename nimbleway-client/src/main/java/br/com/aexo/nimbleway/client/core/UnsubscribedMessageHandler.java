package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.core.messages.UnsubscribedMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

@Component
class UnsubscribedMessageHandler implements MessageHandler<UnsubscribedMessage> {
	private WaitBrokerCommunication wait;

	public UnsubscribedMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(UnsubscribedMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		UnsubscribeMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(MessageType.UNSUBSCRIBE, message.getRequestId()));
		
		Subscription subscription = requestMessage.getSubscription();
		rawSession.remove(subscription);
		requestMessage.resolve(subscription);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof UnsubscribedMessage;
	}
}
