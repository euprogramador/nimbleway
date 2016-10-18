package nimbleway.handlers;

import nimbleway.messages.WampMessage;


public interface MessageHandler<T extends WampMessage> {
	
	void handle(T wampMessage);
	
	boolean isHandleOf(WampMessage msg);

}
