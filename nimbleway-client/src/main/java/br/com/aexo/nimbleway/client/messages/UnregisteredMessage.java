package br.com.aexo.nimbleway.client.messages;

public class UnregisteredMessage implements ClientMessage {

	private Long requestId;

	public UnregisteredMessage( Long requestId) {
		this.requestId = requestId;
	}


	public Long getRequestId() {
		return requestId;
	}
	
}
