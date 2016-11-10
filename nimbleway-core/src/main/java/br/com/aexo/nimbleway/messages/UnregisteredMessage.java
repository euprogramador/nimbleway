package br.com.aexo.nimbleway.messages;

public class UnregisteredMessage implements WampMessage {

	private Long requestId;

	public UnregisteredMessage( Long requestId) {
		this.requestId = requestId;
	}


	public Long getRequestId() {
		return requestId;
	}
	
}
