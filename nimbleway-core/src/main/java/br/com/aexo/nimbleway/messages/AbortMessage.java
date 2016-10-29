package br.com.aexo.nimbleway.messages;

/**
 * represent abort message
 * 
 * @author carlosr
 *
 */
public class AbortMessage implements WampMessage {

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
