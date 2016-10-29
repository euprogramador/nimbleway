package br.com.aexo.nimbleway.messages;

/**
 * represent wamp registered message
 * 
 * @author carlosr
 *
 */

public class RegisteredMessage implements WampMessage {

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
