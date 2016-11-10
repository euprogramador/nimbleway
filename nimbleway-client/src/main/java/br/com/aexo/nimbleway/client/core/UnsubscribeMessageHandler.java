package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

@Component
class UnsubscribeMessageHandler implements MessageHandler<UnsubscribeMessage> {

	private WampTransport transport;
	private WaitBrokerCommunication wait;

	public UnsubscribeMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(UnsubscribeMessage message, ClientSession session) {
		wait.wait(new BrokerCommunicationKey(MessageType.UNSUBSCRIBE, message.getId()), message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof UnsubscribeMessage;
	}

}
