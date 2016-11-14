package br.com.aexo.nimbleway.client.messages;

public class UnsubscribedMessage implements ClientMessage {

	private Long requestId;

	public UnsubscribedMessage( Long requestId) {
		this.requestId = requestId;
	}


	public Long getRequestId() {
		return requestId;
	}
	
}
