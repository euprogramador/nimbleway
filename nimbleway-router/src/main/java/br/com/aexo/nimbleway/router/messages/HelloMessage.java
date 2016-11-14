package br.com.aexo.nimbleway.router.messages;

import br.com.aexo.nimbleway.router.connection.RouterMessage;

public class HelloMessage implements RouterMessage {

	private final String realm;

	public HelloMessage(String realm) {
		this.realm = realm;
	}

	public String getRealm() {
		return realm;
	}
}
