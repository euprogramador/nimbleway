package br.com.aexo.nimbleway.client.messages;

/**
 * represent wamp welcome message
 * 
 * @author carlosr
 *
 */
public class WelcomeMessage implements ClientMessage {

	private Long sessionId;
	private String agent;

	public WelcomeMessage(Long sessionId, String agent) {
		this.sessionId = sessionId;
		this.agent = agent;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public String getAgent() {
		return agent;
	}
	
}
