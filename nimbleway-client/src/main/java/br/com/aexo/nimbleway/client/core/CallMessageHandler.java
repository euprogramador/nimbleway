package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.CallMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle call messages, saving calls in temporary storage for future processing
 * 
 * @author carlosr
 *
 */
@Component
class CallMessageHandler implements MessageHandler<CallMessage> {

	private WaitBrokerCommunication wait;
	private WampTransport transport;

	public CallMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(CallMessage message,ClientSession session) {
		wait.wait(new BrokerCommunicationKey(MessageType.CALL, message.getId()),message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof CallMessage;
	}
}
