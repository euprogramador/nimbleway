package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle call messages, saving calls in temporary storage for future processing
 * 
 * @author carlosr
 *
 */
@Component
public class CallMessageHandler implements MessageHandler<CallMessage> {

	private WaitBrokerCommunication wait;
	private WampTransport transport;

	public CallMessageHandler(WaitBrokerCommunication wait, WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(CallMessage message,ClientSession session) {
		wait.wait(new BrokerCommunicationKey(TypeMessage.CALL, message.getId()),message);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof CallMessage;
	}
}
