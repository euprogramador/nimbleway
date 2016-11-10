package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

@Component
public class UnsubscribeMessageHandler implements MessageHandler<UnsubscribeMessage> {

	private WampTransport transport;
	private WaitBrokerCommunication wait;

	public UnsubscribeMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(UnsubscribeMessage message, ClientSession session) {
		wait.wait(new BrokerCommunicationKey(TypeMessage.UNSUBSCRIBE, message.getId()), message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof UnsubscribeMessage;
	}

}
