package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.PublishMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

@Component
public class PublishMessageHandler implements MessageHandler<PublishMessage> {

	private WampTransport transport;
	private WaitBrokerCommunication wait;

	public PublishMessageHandler(WaitBrokerCommunication wait,WampTransport transport) {
		this.wait = wait;
		this.transport = transport;
	}

	@Override
	public void handle(PublishMessage message,ClientSession session) {
		
		Boolean acknowledge = (Boolean) message.getPublication().getOptions().get("acknowledge");
		if (acknowledge != null && acknowledge){
			wait.wait(new BrokerCommunicationKey(TypeMessage.PUBLISH, message.getId()),message);
		}
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof PublishMessage;
	}

}
