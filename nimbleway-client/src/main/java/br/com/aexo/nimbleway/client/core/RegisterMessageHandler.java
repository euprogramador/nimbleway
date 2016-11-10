package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.RegisterMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

@Component
class RegisterMessageHandler implements MessageHandler<RegisterMessage> {

	private WaitBrokerCommunication wait;
	private WampTransport transport;

	public RegisterMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(RegisterMessage message, ClientSession session) {
		wait.wait(new BrokerCommunicationKey(MessageType.REGISTER,message.getId()), message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisterMessage;
	}

}
