package nimbleway.messages;


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
