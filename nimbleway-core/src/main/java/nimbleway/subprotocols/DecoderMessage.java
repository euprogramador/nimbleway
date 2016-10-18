package nimbleway.subprotocols;

import nimbleway.messages.WampMessage;

public interface DecoderMessage<T extends WampMessage> {
	
	T decode(Object o);
	
	boolean isDecodeOf(Integer messageIdType);

}
