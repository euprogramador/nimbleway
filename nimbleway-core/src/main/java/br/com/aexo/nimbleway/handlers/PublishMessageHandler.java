package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.PublishMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampCall;
import br.com.aexo.nimbleway.storage.WampPublications;

/**
 * handler responsible for recording functions for subsequent calls
 * 
 * @author carlosr
 *
 */
@Component
public class PublishMessageHandler implements MessageHandler<PublishMessage> {

	private WampTransport transport;
	private WampPublications publications;

	public PublishMessageHandler(WampPublications publications,WampTransport transport) {
		this.publications = publications;
		this.transport = transport;
	}

	@Override
	public void handle(PublishMessage message) {
		WampCall call = new WampCall(message.getId(), message.getDefered(), message.getPromise());
		publications.registerCall(call);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof PublishMessage;
	}

}
