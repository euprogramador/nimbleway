package nimbleway;

import java.util.function.Consumer;


public class WampFunction {

	private Long idWaitRegister;
	private Consumer<WampInvocation> fn;
	private String name;

	public WampFunction(Long idWaitRegister, String name) {
		super();
		this.idWaitRegister = idWaitRegister;
		this.name = name;
	}

	public void onCallback(Consumer<WampInvocation> fn) {
		this.fn = fn;

	}

	public Long getIdWaitRegister() {
		return idWaitRegister;
	}

	public Consumer<WampInvocation> getFn() {
		return fn;
	}

	public String getName() {
		return name;
	}

}
