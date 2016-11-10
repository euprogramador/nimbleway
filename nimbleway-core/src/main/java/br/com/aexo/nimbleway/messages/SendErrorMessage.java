package br.com.aexo.nimbleway.messages;

import br.com.aexo.nimbleway.ResultError;
import br.com.aexo.nimbleway.TypeMessage;

public class SendErrorMessage implements WampMessage {

	private TypeMessage type;
	private Long requestId;
	private ResultError resultError;

	public SendErrorMessage(TypeMessage type, Long requestId, ResultError resultError) {
		this.type = type;
		this.requestId = requestId;
		this.resultError = resultError;
	}

	public TypeMessage getType() {
		return type;
	}

	public Long getRequestId() {
		return requestId;
	}

	public ResultError getResultError() {
		return resultError;
	}

}
