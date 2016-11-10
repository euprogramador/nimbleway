package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.Registration;
import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.DefaultClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.UnregisterMessage;
import br.com.aexo.nimbleway.messages.UnregisteredMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

@Component
public class UnregisteredMessageHandler implements MessageHandler<UnregisteredMessage> {
	private WaitBrokerCommunication wait;

	public UnregisteredMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(UnregisteredMessage message, ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		UnregisterMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(TypeMessage.UNREGISTER,message.getRequestId()));

		Registration registration = requestMessage.getRegistration();
		rawSession.remove(registration);
		requestMessage.resolve(registration);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof UnregisteredMessage;
	}
}
