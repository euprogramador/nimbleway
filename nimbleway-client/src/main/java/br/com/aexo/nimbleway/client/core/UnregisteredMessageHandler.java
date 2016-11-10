package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.messages.UnregisterMessage;
import br.com.aexo.nimbleway.core.messages.UnregisteredMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

@Component
class UnregisteredMessageHandler implements MessageHandler<UnregisteredMessage> {
	private WaitBrokerCommunication wait;

	public UnregisteredMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(UnregisteredMessage message, ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		UnregisterMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(MessageType.UNREGISTER,message.getRequestId()));

		Registration registration = requestMessage.getRegistration();
		rawSession.remove(registration);
		requestMessage.resolve(registration);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof UnregisteredMessage;
	}
}
