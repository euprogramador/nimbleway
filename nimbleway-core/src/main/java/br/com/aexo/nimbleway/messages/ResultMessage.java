package br.com.aexo.nimbleway.messages;

import br.com.aexo.nimbleway.WampMessage;

import com.fasterxml.jackson.databind.JsonNode;

public class ResultMessage extends WampMessage {

	private final JsonNode result;
	private final Long idCall;

	public ResultMessage(Long idCall, JsonNode result) {
		this.idCall = idCall;
		this.result = result;
	}

	public JsonNode getResult() {
		return result;
	}

	public Long getIdCall() {
		return idCall;
	}

}
