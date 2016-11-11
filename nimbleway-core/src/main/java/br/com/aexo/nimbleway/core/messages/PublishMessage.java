package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.Publication;

public class PublishMessage implements WampMessage {

	private Long id;
	private Publication publication;

	public PublishMessage(Publication publication) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.publication = publication;
	}

	public Long getId() {
		return id;
	}

	public Publication getPublication() {
		return publication;
	}

}
