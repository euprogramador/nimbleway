package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.SubscribeMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handler responsible for recording functions for subsequent calls
 * 
 * @author carlosr
 *
 */
@Component
public class SubscribeMessageHandler implements MessageHandler<SubscribeMessage> {

	private WampTransport transport;
	private WaitBrokerCommunication wait;

	public SubscribeMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(SubscribeMessage message, ClientSession session) {
		wait.wait(new BrokerCommunicationKey(TypeMessage.SUBSCRIBE, message.getId()),message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof SubscribeMessage;
	}

}
