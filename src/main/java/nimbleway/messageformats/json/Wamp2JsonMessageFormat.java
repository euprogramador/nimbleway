package nimbleway.messageformats.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nimbleway.MessageFormat;
import nimbleway.WampMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


/**
 * classe que decodifica o subprotocolo usado no wamp
 * 
 * @author carlosr
 *
 */
public class Wamp2JsonMessageFormat implements MessageFormat {

	private ObjectMapper objectMapper;


	
	

	public Wamp2JsonMessageFormat(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		
		Map<Integer,MessageFormat> messageFormats = new HashMap<Integer, MessageFormat>();
		
		// decode hello message
		messageFormats.put(1, new MessageFormat() {
			
			@Override
			public WampMessage decode(Object data) {
				
				return null;
			}
		});
		
	}
	
	@Override
	public WampMessage decode(Object data) {
		String msg = data.toString();
		ArrayNode value;
		try {
			value = objectMapper.readValue(msg, ArrayNode.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		value.get(0);
		
		

	}

}
