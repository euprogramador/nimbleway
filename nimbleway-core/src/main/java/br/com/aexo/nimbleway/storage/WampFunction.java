package br.com.aexo.nimbleway.storage;

import java.util.function.Consumer;

import br.com.aexo.nimbleway.WampInvocationResult;

/**
 * represent one function registred in wamp session
 * 
 * @author carlosr
 *
 */

public class WampFunction<T> {

	private Long idWaitRegister;
	private Consumer<T> fn;
	private String name;

	public WampFunction(Long idWaitRegister, String name) {
		super();
		this.idWaitRegister = idWaitRegister;
		this.name = name;
	}

	public void onCallback(Consumer<T> fn) {
		this.fn = fn;

	}

	public Long getIdWaitRegister() {
		return idWaitRegister;
	}

	public Consumer<T> getFn() {
		return fn;
	}

	public String getName() {
		return name;
	}

}
