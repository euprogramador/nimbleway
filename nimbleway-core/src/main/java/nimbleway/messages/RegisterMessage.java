package nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import nimbleway.WampInvocation;


public class RegisterMessage implements WampMessage {

	private String name;
	private Consumer<WampInvocation> fn;
	private Long id;

	public RegisterMessage(String name, Consumer<WampInvocation> fn) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.name = name;
		this.fn = fn;
	}

	public String getName() {
		return name;
	}

	public Consumer<WampInvocation> getFn() {
		return fn;
	}

	public Long getId() {
		return id;
	}

}