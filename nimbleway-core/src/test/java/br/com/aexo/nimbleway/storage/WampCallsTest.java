package br.com.aexo.nimbleway.storage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.aexo.nimbleway.WampResult;

public class WampCallsTest {
	
	private WampCalls storage;
	private DeferredObject<WampResult, Exception, Object> def;
	private Promise<WampResult, Exception, Object> promise;
	private WampCall call;
	
	@Mock
	private WampResult result;

	@Before
	public void setup(){
		initMocks(this);
		storage = new WampCalls(); 

		def = new DeferredObject<WampResult, Exception, Object>();
		promise = def.promise();
		call = new WampCall(1L, def, promise);
	}
	
	@Test
	public void shouldBeRegisterCallInTemporaryStorage(){
		storage.registerCall(call);

		assertThat(storage.getCalls().containsValue(call), is(true));
	}

	@Test
	public void shouldBeResolvePromiseOfResolveCall(){
		storage.registerCall(call);
		
		call.getPromise().then((result)->{
			result.as(String.class);
		});
		
		storage.resolve(call.getId(), result);
		verify(result).as(String.class);
	}
	
	@Test
	public void shouldBeOnResolveCallRemoveCallInTemporaryStorage(){
		storage.registerCall(call);
		storage.resolve(call.getId(), result);
		
		assertThat(storage.getCalls().containsValue(call), is(false));
	
	}
	
}
