package br.com.aexo.nimbleway.messages;

/**
 * represent wamp welcome message
 * 
 * @author carlosr
 *
 */
public class WelcomeMessage implements WampMessage {

	private Long sessionId;

	public WelcomeMessage(Long sessionId){
		this.sessionId = sessionId;
	}
	
	public Long getSessionId() {
		return sessionId;
	}
	
}
