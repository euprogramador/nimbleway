package br.com.aexo.nimbleway.messages;

/**
 * represent wamp subscribed message
 * 
 * @author carlosr
 *
 */

public class SubscribedMessage implements WampMessage {

	private Long requestId;
	private Long registrationId;

	public SubscribedMessage(Long requestId, Long registrationId) {
		this.requestId = requestId;
		this.registrationId = registrationId;
	}

	public Long getRequestId() {
		return requestId;
	}


	public Long getRegistrationId() {
		return registrationId;
	}

}
