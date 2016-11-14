package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.Registration;

public class RegisterMessage implements ClientMessage {

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
