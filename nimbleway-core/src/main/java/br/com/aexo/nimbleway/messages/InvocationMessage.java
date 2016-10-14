package br.com.aexo.nimbleway.messages;

import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.WampMessage;

public class InvocationMessage extends WampMessage {

	private final Long idRequest;
	private final Long idFunctionRegistration;
	private final ArrayNode params;

	public InvocationMessage(Long idRequest, Long idFunctionRegistration,
			ArrayNode params) {
		this.idRequest = idRequest;
		this.idFunctionRegistration = idFunctionRegistration;
		this.params = params;
	}

	public Long getIdRequest() {
		return idRequest;
	}

	public Long getIdFunctionRegistration() {
		return idFunctionRegistration;
	}

	public ArrayNode getParams() {
		return params;
	}

}
