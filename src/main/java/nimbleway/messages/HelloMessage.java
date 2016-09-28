package nimbleway.messages;

import nimbleway.WampMessage;

public class HelloMessage implements WampMessage {

	private final String realm;

	public HelloMessage(String realm) {
		this.realm = realm;
	}

	public String getRealm() {
		return realm;
	}
	
}
