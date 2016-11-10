package br.com.aexo.nimbleway.client.core;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * base for wamp message handlers
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface MessageHandler<T extends WampMessage> {

	void handle(T wampMessage,ClientSession session);

	boolean isHandleOf(WampMessage msg);

}