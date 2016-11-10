package br.com.aexo.nimbleway.messages;

/**
 * represent wamp registered message
 * 
 * @author carlosr
 *
 */

public class RegisteredMessage implements WampMessage {

	private Long requestId;
	private Long registrationId;

	public RegisteredMessage(Long requestId, Long registrationId) {
		this.requestId = requestId;
		this.registrationId = registrationId;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public Long getRequestId() {
		return requestId;
	}
}
