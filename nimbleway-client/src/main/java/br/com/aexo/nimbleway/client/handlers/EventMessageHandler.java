package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.DefaultClientSession;
import br.com.aexo.nimbleway.messages.EventMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle invocationMessage calling registred function
 * 
 * @author carlosr
 *
 */

@Component
public class EventMessageHandler implements MessageHandler<EventMessage> {


	public void handle(EventMessage message,ClientSession session) {
		DefaultClientSession rawSession = (DefaultClientSession) session;
		rawSession.dispachEvent(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof EventMessage;
	}

}
