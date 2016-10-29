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
import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunctions;

public class InvocationMessageHandlerTest {

	@Mock
	private WampTransport transport;
	
	@Mock
	private WampFunctions functions;

	private InvocationMessageHandler handler;
	
	@Mock
	private InvocationMessage message;

	@Before
	public void setup() {
		initMocks(this);
		handler = new InvocationMessageHandler(transport, functions);
	}
	
	@Test
	public void shouldBeInvokeRegistredFunction(){
		handler.handle(message);
		verify(functions).callRegistredFn(Mockito.anyLong(), Mockito.any());
	}
	
	@Test
	public void shouldBeHandleInvocationMessageOnly(){
		assertThat(handler.isHandleOf(new WampMessage() {
		}),is(false));
		
		assertThat(handler.isHandleOf(message),is(true));
		
		
	}

}
