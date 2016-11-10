package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.Publication;
import br.com.aexo.nimbleway.core.messages.PublishMessage;
import br.com.aexo.nimbleway.core.messages.PublishedMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle RegistredMessage recording function in session
 * 
 * @author carlosr
 *
 */
@Component
class PublishedMessageHandler implements MessageHandler<PublishedMessage> {

	private WaitBrokerCommunication wait;

	public PublishedMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(PublishedMessage message,ClientSession session) {
		PublishMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(MessageType.PUBLISH, message.getRequestId()));
		
		Publication publication = requestMessage.getPublication().publicationId(message.getPublicationId());
		requestMessage.resolve(publication);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof PublishedMessage;
	}
}
