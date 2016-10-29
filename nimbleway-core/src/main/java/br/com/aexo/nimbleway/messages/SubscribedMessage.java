package br.com.aexo.nimbleway.messages;

/**
 * represent wamp subscribed message
 * 
 * @author carlosr
 *
 */

public class SubscribedMessage implements WampMessage {

	private Long idRequest;
	private Long idRegistration;

	public SubscribedMessage(Long idRequest, Long idRegistration) {
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
