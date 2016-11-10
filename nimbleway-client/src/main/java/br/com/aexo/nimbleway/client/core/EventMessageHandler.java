package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.messages.EventMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle invocationMessage calling registred function
 * 
 * @author carlosr
 *
 */

@Component
class EventMessageHandler implements MessageHandler<EventMessage> {


	public void handle(EventMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		rawSession.dispachEvent(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof EventMessage;
	}

}
