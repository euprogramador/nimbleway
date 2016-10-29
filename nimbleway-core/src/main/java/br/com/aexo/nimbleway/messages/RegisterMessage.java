package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.WampInvocationResult;

/**
 * represent wamp register message solicitation
 * 
 * @author carlosr
 *
 */
public class RegisterMessage implements WampMessage {

	private String name;
	private Consumer<WampInvocationResult> fn;
	private Long id;

	public RegisterMessage(String name, Consumer<WampInvocationResult> fn) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.name = name;
		this.fn = fn;
	}

	public String getName() {
		return name;
	}

	public Consumer<WampInvocationResult> getFn() {
		return fn;
	}

	public Long getId() {
		return id;
	}

}
