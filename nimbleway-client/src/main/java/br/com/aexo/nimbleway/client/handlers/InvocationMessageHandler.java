package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.DefaultClientSession;
import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle invocationMessage calling registred function
 * 
 * @author carlosr
 *
 */

@Component
public class InvocationMessageHandler implements MessageHandler<InvocationMessage> {


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
