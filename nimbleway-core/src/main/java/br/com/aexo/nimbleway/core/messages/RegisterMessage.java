package br.com.aexo.nimbleway.core.messages;

import br.com.aexo.nimbleway.core.Registration;

public class RegisterMessage implements WampMessage {

	private Long id;
	private Registration registration;

	public RegisterMessage(Long id,Registration registration) {
		this.id = id;
		this.registration = registration;
	}

	public Long getId() {
		return id;
	}

	public Registration getRegistration() {
		return registration;
	}

}
