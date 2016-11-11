package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.Invocation;

public class CallMessage implements WampMessage {

	private Long id;

	private Invocation invocation;

	public CallMessage(Invocation invocation) {
		this.invocation = invocation;
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
	}

	public Long getId() {
		return id;
	}

	public Invocation getInvocation() {
		return invocation;
	}

}
