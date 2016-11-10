package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.Registration;
import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.DefaultClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.RegisteredMessage;
import br.com.aexo.nimbleway.messages.WampMessage;


@Component
public class RegisteredMessageHandler implements MessageHandler<RegisteredMessage> {

	private WaitBrokerCommunication wait;

	public RegisteredMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(RegisteredMessage message, ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		RegisterMessage registerMessage = wait.retrieve(new BrokerCommunicationKey(TypeMessage.REGISTER,message.getRequestId()));
		Registration registration = registerMessage.getRegistration().registrationId(message.getRegistrationId());
		rawSession.save(registration);
		registerMessage.resolve(registration);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisteredMessage;
	}
}
