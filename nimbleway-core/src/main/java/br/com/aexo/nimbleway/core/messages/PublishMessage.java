package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.Publication;

public class PublishMessage implements WampMessage {

	private Long id;
	private Publication publication;

	public PublishMessage(Long id,Publication publication) {
		this.id = id;
		this.publication = publication;
	}

	public Long getId() {
		return id;
	}

	public Publication getPublication() {
		return publication;
	}

}
