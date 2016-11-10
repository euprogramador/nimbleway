package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.Publication;
import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.PublishMessage;
import br.com.aexo.nimbleway.messages.PublishedMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle RegistredMessage recording function in session
 * 
 * @author carlosr
 *
 */
@Component
public class PublishedMessageHandler implements MessageHandler<PublishedMessage> {

	private WaitBrokerCommunication wait;

	public PublishedMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(PublishedMessage message,ClientSession session) {
		PublishMessage requestMessage = wait.retrieve(new BrokerCommunicationKey(TypeMessage.PUBLISH, message.getRequestId()));
		
		Publication publication = requestMessage.getPublication().publicationId(message.getPublicationId());
		requestMessage.resolve(publication);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof PublishedMessage;
	}
}
