package br.com.aexo.nimbleway.core;

/**
 * exception for wamp errors
 * 
 * @author carlosr
 *
 */
public class WampException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String wampError;

	public WampException(String message) {
		super(message);
	}

	public WampException(String message, String wampError) {
		super(message);
		this.wampError = wampError;
	}

	public String getWampError() {
		return wampError;
	}

}
