package br.com.aexo.nimbleway.handlers;

import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * base for wamp message handlers
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface MessageHandler<T extends WampMessage> {

	void handle(T wampMessage);

	boolean isHandleOf(WampMessage msg);

}
