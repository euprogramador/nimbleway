package br.com.aexo.nimbleway.client.messages;

/**
 * represent wamp hello message
 * 
 * @author carlosr
 *
 */
public class HelloMessage implements ClientMessage {

	private String reaml;

	public HelloMessage(String reaml) {
		this.reaml = reaml;
	}

	public String getReaml() {
		return reaml;
	}

	public void setReaml(String reaml) {
		this.reaml = reaml;
	}

}
