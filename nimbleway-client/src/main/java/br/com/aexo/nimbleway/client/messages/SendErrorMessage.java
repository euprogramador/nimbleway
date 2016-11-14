package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.MessageType;
import br.com.aexo.nimbleway.client.interaction.ResultError;

public class SendErrorMessage implements ClientMessage {

	private MessageType type;
	private Long requestId;
	private ResultError resultError;

	public SendErrorMessage(MessageType type, Long requestId, ResultError resultError) {
		this.type = type;
		this.requestId = requestId;
		this.resultError = resultError;
	}

	public MessageType getType() {
		return type;
	}

	public Long getRequestId() {
		return requestId;
	}

	public ResultError getResultError() {
		return resultError;
	}

}
