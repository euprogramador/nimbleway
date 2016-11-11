package br.com.aexo.nimbleway.core.messages;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.Registration;

public class RegisterMessage implements WampMessage {

	private Long id;
	private Registration registration;

	public RegisterMessage(Registration registration) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.registration = registration;
	}

	public Long getId() {
		return id;
	}

	public Registration getRegistration() {
		return registration;
	}

}
