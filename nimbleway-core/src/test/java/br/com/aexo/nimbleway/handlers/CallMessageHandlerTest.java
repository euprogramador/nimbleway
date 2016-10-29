package br.com.aexo.nimbleway.handlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.com.aexo.nimbleway.WampResult;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampCall;
import br.com.aexo.nimbleway.storage.WampCalls;

public class CallMessageHandlerTest {
	
	@Mock
	private WampCalls callsStorage;
	
	@Mock
	private CallMessage message;
	
	@Mock
	private Deferred<WampResult, Exception, Object> deferred;
	
	@Mock
	private Promise<WampResult, Exception, Object> promise;
	
	private Long id = 1L;
	
	
	
	private CallMessageHandler handler;

	@Mock
	private WampTransport transport;
	
	@Before
	public void setup(){
		initMocks(this);
		handler = new CallMessageHandler(callsStorage, transport);
	}
	
	@Test
	public void shouldBeRegisterCallForProcessInFutureViaPromises(){
		
		CallMessageHandlerTest me = this;

		doAnswer(new Answer<Void>() {
			
			@Override
			public Void answer(InvocationOnMock invocation)	throws Throwable {
				WampCall object = (WampCall) invocation.getArguments()[0];
				Deferred<WampResult, Exception, ?> deferred = object.getDeferred();
				Promise<WampResult, Exception, ?> promise = object.getPromise();
				Long id = object.getId();
				
				assertThat(id, is(me.id));
				assertThat(promise, is(me.promise));
				assertThat(deferred, is(me.deferred));
				
				return null;
			}
			
		}).when(callsStorage).registerCall(Mockito.anyObject());
		
		when(message.getId()).thenReturn(id);
		when(message.getDefered()).thenReturn(deferred);
		when(message.getPromise()).thenReturn(promise);
		
		handler.handle(message);
		
		verify(callsStorage).registerCall(Mockito.anyObject());
		verify(transport).write(Mockito.any());
	}
	
	@Test
	public void shouldBeHandleCallMessageOnly(){
		assertThat(handler.isHandleOf(new WampMessage() {}),is(false));
		assertThat(handler.isHandleOf(message),is(true));
	}

}
