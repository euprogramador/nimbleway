package br.com.aexo.nimbleway.handlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunctions;

public class RegisterMessageHandlerTest {

	@Mock
	private WampFunctions functions;

	private RegisterMessageHandler handler;

	@Mock
	private RegisterMessage message;

	@Mock
	private WampTransport transport;

	@Before
	public void setup() {
		initMocks(this);
		handler = new RegisterMessageHandler(functions, transport);
	}

	@Test
	public void shouldBeRegisterFunctionInStorage() {
		handler.handle(message);

		verify(functions).waitRegistration(Mockito.anyObject());
		verify(transport).write(Mockito.any());
	}

	public void shouldBeHandleRegisterMessageOnly() {
		assertThat(handler.isHandleOf(new WampMessage() {
		}), is(false));

		assertThat(handler.isHandleOf(message), is(true));
	}
}
