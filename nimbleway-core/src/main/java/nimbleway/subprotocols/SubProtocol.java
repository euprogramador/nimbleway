package nimbleway.subprotocols;

import nimbleway.messages.WampMessage;


public interface SubProtocol {
	
	String getName();
	
	Object encode(WampMessage message);
	
	WampMessage decode(Object o);

}
