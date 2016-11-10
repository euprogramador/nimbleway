package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.messages.InvocationMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle invocationMessage calling registred function
 * 
 * @author carlosr
 *
 */

@Component
class InvocationMessageHandler implements MessageHandler<InvocationMessage> {


	public InvocationMessageHandler() {
	}

	@Override
	public void handle(InvocationMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		rawSession.invoke(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof InvocationMessage;
	}

}
