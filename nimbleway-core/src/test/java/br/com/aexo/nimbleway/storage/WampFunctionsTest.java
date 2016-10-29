package br.com.aexo.nimbleway.storage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import br.com.aexo.nimbleway.WampInvocationResult;

public class WampFunctionsTest {

	private WampFunctions funcs;

	private WampFunction function;

	private Boolean req;

	@Before
	public void setup() {
		funcs = new WampFunctions();
		function = new WampFunction(1l, "teste");
	}

	@Test
	public void shouldBeWaitRegistration() {
		funcs.waitRegistration(function);

		assertThat(funcs.getWaitRegistration().size(), is(1));
	}
	
	@Test
	public void shouldBeRegistryFunctionAndRemoveWaitRegistration(){
		
		funcs.waitRegistration(function);

		assertThat(funcs.getWaitRegistration().size(),is(1));
		assertThat(funcs.getRegistredFunction().size(),is(0));

		funcs.registryFunction(function.getIdWaitRegister(), 2L);
		
		assertThat(funcs.getWaitRegistration().size(),is(0));
		assertThat(funcs.getRegistredFunction().size(),is(1));

	}
	
	@Test
	public void shouldBeCallRegistredFunction(){
		funcs.waitRegistration(function);
		funcs.registryFunction(function.getIdWaitRegister(), 2L);
		req = false;
		function.onCallback((request)->req = true);
		
		funcs.callRegistredFn(2L, new WampInvocationResult(null, null));
		assertThat(req, is(true));
	}

	
	
}
