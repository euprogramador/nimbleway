package br.com.aexo.nimbleway.messages;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * represent wamp invocation message
 * 
 * @author carlosr
 *
 */
public class InvocationMessage implements WampMessage {

	private final Long idRequest;
	private final Long idFunctionRegistration;
	private final ArrayNode params;

	public InvocationMessage(Long idRequest, Long idFunctionRegistration, ArrayNode params) {
		this.idRequest = idRequest;
		this.idFunctionRegistration = idFunctionRegistration;
		this.params = params;
	}

	public Long getIdRequest() {
		return idRequest;
	}

	public Long getIdFunctionRegisted() {
		return idFunctionRegistration;
	}

	public ArrayNode getParams() {
		return params;
	}

}
