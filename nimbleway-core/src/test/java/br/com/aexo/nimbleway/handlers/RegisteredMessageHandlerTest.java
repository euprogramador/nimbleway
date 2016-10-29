package br.com.aexo.nimbleway.handlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.aexo.nimbleway.messages.RegisteredMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunctions;

public class RegisteredMessageHandlerTest {
	
	@Mock
	private WampFunctions functions;
	
	private RegisteredMessageHandler handler;
	
	@Mock
	private RegisteredMessage message;

	@Before
	public void setup(){
		
		initMocks(this);
		
		handler = new RegisteredMessageHandler(functions);
	}
	
	@Test
	public void shouldBeSaveFunctionRegistration(){
		handler.handle(message);
		verify(functions).registryFunction(Mockito.anyLong(), Mockito.anyLong());
	}
	
	@Test
	public void shouldBeHandleRegistrationMessageOnly(){
		assertThat(handler.isHandleOf(new WampMessage() {
		}),is(false));
		
		assertThat(handler.isHandleOf(message),is(true));
	}
	
}
