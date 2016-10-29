package br.com.aexo.nimbleway.handlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.aexo.nimbleway.messages.ResultMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampCalls;

public class ResultMessageHandlerTest {

	@Mock
	private WampCalls calls;

	private ResultMessageHandler handler;

	@Mock
	private ResultMessage message;

	@Before
	public void setup() {
		initMocks(this);
		handler = new ResultMessageHandler(calls);
	}

	@Test
	public void shouldBeResolveResultInvocation() {
		handler.handle(message);

		verify(calls).resolve(Mockito.anyLong(), Mockito.any());
	}

	@Test
	public void shouldBeHandleResultMessageOnly() {
		assertThat(handler.isHandleOf(new WampMessage() {
		}), is(false));

		assertThat(handler.isHandleOf(message), is(true));

	}

}
