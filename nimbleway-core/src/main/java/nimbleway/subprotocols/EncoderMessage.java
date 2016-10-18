package nimbleway.subprotocols;

import nimbleway.messages.WampMessage;


public interface EncoderMessage<T extends WampMessage> {
	
	Object encode(T message);

	boolean isEncodeOf(WampMessage type);
	
}
