package br.com.aexo.nimbleway.messages;

/**
 * represent wamp hello message
 * 
 * @author carlosr
 *
 */
public class HelloMessage implements WampMessage {

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
