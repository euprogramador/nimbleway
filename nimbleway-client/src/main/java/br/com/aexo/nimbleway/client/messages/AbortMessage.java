package br.com.aexo.nimbleway.client.messages;

/**
 * represent abort message
 * 
 * @author carlosr
 *
 */
public class AbortMessage implements ClientMessage {

	private String message;
	private String wampError;

	public AbortMessage(String message, String wampError) {
		super();
		this.message = message;
		this.wampError = wampError;
	}

	public String getMessage() {
		return message;
	}

	public String getWampError() {
		return wampError;
	}

}
