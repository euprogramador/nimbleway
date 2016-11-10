package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

@Component
public class RegisterMessageHandler implements MessageHandler<RegisterMessage> {

	private WaitBrokerCommunication wait;
	private WampTransport transport;

	public RegisterMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(RegisterMessage message, ClientSession session) {
		wait.wait(new BrokerCommunicationKey(TypeMessage.REGISTER,message.getId()), message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisterMessage;
	}

}
