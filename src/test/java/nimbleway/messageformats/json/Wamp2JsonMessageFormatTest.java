package nimbleway.messageformats.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;

import nimbleway.WampMessage;

public class Wamp2JsonMessageFormatTest {

	private Wamp2JsonMessageFormat messageFormat;
	
	@Mock
	private ObjectMapper objectMapper;

	
	@Before
	public void setup(){
		initMocks(this);
		messageFormat = new Wamp2JsonMessageFormat(objectMapper);
	}
	
	@Test
	public void shouldBeParseJSONMessage(){
		
		// deveria decodificar parcialmente o tipo de mensagem e escolher o decoder adequado.
		
		String msg = "[1,\"somerealm\",{\"roles\":{\"publisher\":{},\"subscriber\":{}}}]";
		WampMessage decoded = messageFormat.decode(msg);
		assertThat(decoded, is(notNullValue()));
	}
	
}
