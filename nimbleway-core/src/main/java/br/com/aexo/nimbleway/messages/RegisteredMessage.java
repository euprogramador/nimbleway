package br.com.aexo.nimbleway.messages;

import br.com.aexo.nimbleway.WampMessage;

public class RegisteredMessage extends WampMessage {

	private Long idRequest;
	private Long idRegistration;

	public RegisteredMessage(Long idRequest, Long idRegistration) {
		this.idRequest = idRequest;
		this.idRegistration = idRegistration;
	}

	public Long getIdRequest() {
		return idRequest;
	}

	public Long getIdRegistration() {
		return idRegistration;
	}

}
