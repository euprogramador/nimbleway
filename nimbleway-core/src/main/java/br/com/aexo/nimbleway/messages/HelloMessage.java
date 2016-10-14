package br.com.aexo.nimbleway.messages;

import br.com.aexo.nimbleway.WampMessage;

public class HelloMessage extends WampMessage {

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
