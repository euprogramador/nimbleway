package br.com.aexo.nimbleway.core.messages;

public class UnsubscribedMessage implements WampMessage {

	private Long requestId;

	public UnsubscribedMessage( Long requestId) {
		this.requestId = requestId;
	}


	public Long getRequestId() {
		return requestId;
	}
	
}
