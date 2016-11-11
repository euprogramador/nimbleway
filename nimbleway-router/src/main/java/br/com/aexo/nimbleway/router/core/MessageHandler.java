package br.com.aexo.nimbleway.router.core;

import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.router.WampRouter;

public interface MessageHandler<T extends WampMessage> {

	void handle(T message, WampRouter router);

}
