package br.com.aexo.nimbleway.router.messages;

import br.com.aexo.nimbleway.router.connection.RouterMessage;

public class WelcomeMessage implements RouterMessage {

	private long sessionId;
	private String agent;

	public WelcomeMessage(long sessionId, String agent) {
		this.sessionId = sessionId;
		this.agent = agent;
	}

	public long getSessionId() {
		return sessionId;
	}

	public String getAgent() {
		return agent;
	}

}
