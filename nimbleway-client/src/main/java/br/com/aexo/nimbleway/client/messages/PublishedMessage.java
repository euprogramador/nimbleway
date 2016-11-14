package br.com.aexo.nimbleway.client.messages;

public class PublishedMessage implements ClientMessage {

	private Long requestId;
	private Long publicationId;

	public PublishedMessage(Long requestId, Long publicationId) {
		super();
		this.requestId = requestId;
		this.publicationId = publicationId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public Long getPublicationId() {
		return publicationId;
	}
}
