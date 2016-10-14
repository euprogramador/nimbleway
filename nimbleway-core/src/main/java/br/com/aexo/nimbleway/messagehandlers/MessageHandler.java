package br.com.aexo.nimbleway.messagehandlers;

import br.com.aexo.nimbleway.WampMessage;

public interface MessageHandler<T extends WampMessage> {

	void handle(T message,MessageHandlerContext context);
	
}
