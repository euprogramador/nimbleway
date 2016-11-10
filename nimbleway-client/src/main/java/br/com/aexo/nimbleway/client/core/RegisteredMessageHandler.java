package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.messages.RegisterMessage;
import br.com.aexo.nimbleway.core.messages.RegisteredMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;


@Component
class RegisteredMessageHandler implements MessageHandler<RegisteredMessage> {

	private WaitBrokerCommunication wait;

	public RegisteredMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(RegisteredMessage message, ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		RegisterMessage registerMessage = wait.retrieve(new BrokerCommunicationKey(MessageType.REGISTER,message.getRequestId()));
		Registration registration = registerMessage.getRegistration().registrationId(message.getRegistrationId());
		rawSession.save(registration);
		registerMessage.resolve(registration);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisteredMessage;
	}
}
