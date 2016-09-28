package nimbleway;

import java.io.IOException;

import nimbleway.messageformats.json.Wamp2JsonMessageFormatTest;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.type.ArrayType;

public class Wamp2JsonSubProtocolTest {

	private Wamp2JsonMessageFormatTest protocol;

	@Before
	public void setup() {
		protocol = new Wamp2JsonMessageFormatTest();
	}

	@Test
	public void shouldBeConvertMessage() {

	}

	public static void main(String[] args) throws JsonParseException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		String msg = "[1,\"somerealm\",{\"roles\":{\"publisher\":{},\"subscriber\":{}}}]";
		
		ArrayNode value = mapper.readValue(msg, ArrayNode.class);
		
		System.out.println(value.get(2).getClass());
	}

}
