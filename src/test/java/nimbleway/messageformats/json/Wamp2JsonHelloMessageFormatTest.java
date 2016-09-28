package nimbleway.messageformats.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import nimbleway.messages.HelloMessage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * classe que testa o comportamento do decoder json para uma mensagem hello o
 * decoder deve decodificar string e decodificar arraynode do jackson de forma
 * otimizada.
 * 
 * @author carlosr
 *
 */
public class Wamp2JsonHelloMessageFormatTest {

	private Wamp2JsonHelloMessageFormat mf;

	@Mock
	private ArrayNode arrayNode;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		initMocks(this);
		mf = new Wamp2JsonHelloMessageFormat(new ObjectMapper());
	}

	@Test
	public void shouldBeDecodeJsonStringToHelloMessage() {
		String rawMsg = "[1,\"somerealm\",{\"roles\":{\"publisher\":{},\"subscriber\":{}}}]";
		HelloMessage msg = (HelloMessage) mf.decode(rawMsg);

		assertThat(msg.getRealm(), is("somerealm"));
	}

	@Test
	public void shouldBeDecodeArrayNodeToHelloMessage() throws JsonParseException, JsonMappingException, IOException {
		String rawMsg = "[1,\"somerealm\",{\"roles\":{\"publisher\":{},\"subscriber\":{}}}]";

		ObjectMapper mapper = new ObjectMapper();
		ArrayNode value = mapper.readValue(rawMsg, ArrayNode.class);

		HelloMessage msg = (HelloMessage) mf.decode(value);
		assertThat(msg.getRealm(), is("somerealm"));
	}

}
