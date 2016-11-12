package br.com.aexo.nimbleway.core.messages;

import br.com.aexo.nimbleway.core.Invocation;

public class CallMessage implements WampMessage {

	private Long id;

	private Invocation invocation;

	public CallMessage(Long id,Invocation invocation) {
		this.id = id;
		this.invocation = invocation;
	}

	public Long getId() {
		return id;
	}

	public Invocation getInvocation() {
		return invocation;
	}

}
