package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.WampInvocation;
import br.com.aexo.nimbleway.WampInvocationResult;

/**
 * represent wamp subscribe message solicitation
 * 
 * @author carlosr
 *
 */
public class SubscribeMessage implements WampMessage {

	private String topic;
	private Consumer<WampInvocation> fn;
	private Long id;

	public SubscribeMessage(String topic, Consumer<WampInvocation> fn) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.topic = topic;
		this.fn = fn;
	}

	public String getTopic() {
		return topic;
	}

	public Consumer<WampInvocation> getFn() {
		return fn;
	}

	public Long getId() {
		return id;
	}

}
