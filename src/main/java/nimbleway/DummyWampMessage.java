package nimbleway;


public class DummyWampMessage implements WampMessage {

	private Object data;

	public DummyWampMessage(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DummyWampMessage [data=" + data + "]";
	}

}
