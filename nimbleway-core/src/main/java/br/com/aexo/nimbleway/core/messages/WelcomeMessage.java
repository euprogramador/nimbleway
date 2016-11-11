package br.com.aexo.nimbleway.core.messages;

/**
 * represent wamp welcome message
 * 
 * @author carlosr
 *
 */
public class WelcomeMessage implements WampMessage {

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
