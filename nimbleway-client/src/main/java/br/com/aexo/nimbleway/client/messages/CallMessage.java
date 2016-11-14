package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.Invocation;

public class CallMessage implements ClientMessage {

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
