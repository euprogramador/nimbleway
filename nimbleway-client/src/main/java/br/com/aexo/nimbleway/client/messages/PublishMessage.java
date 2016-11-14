package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.Publication;

public class PublishMessage implements ClientMessage {

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
