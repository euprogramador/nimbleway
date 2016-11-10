package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.PublishMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

@Component
class PublishMessageHandler implements MessageHandler<PublishMessage> {

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
			wait.wait(new BrokerCommunicationKey(MessageType.PUBLISH, message.getId()),message);
		}
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof PublishMessage;
	}

}
