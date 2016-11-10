package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.SubscribeMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handler responsible for recording functions for subsequent calls
 * 
 * @author carlosr
 *
 */
@Component
class SubscribeMessageHandler implements MessageHandler<SubscribeMessage> {

	private WampTransport transport;
	private WaitBrokerCommunication wait;

	public SubscribeMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(SubscribeMessage message, ClientSession session) {
		wait.wait(new BrokerCommunicationKey(MessageType.SUBSCRIBE, message.getId()),message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof SubscribeMessage;
	}

}
