package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampInvocation;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.EventMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampSubscriptions;

/**
 * handle invocationMessage calling registred function
 * 
 * @author carlosr
 *
 */

@Component
public class EventMessageHandler implements MessageHandler<EventMessage> {

	private WampSubscriptions subscriptions;
	private WampTransport transport;

	public EventMessageHandler(WampSubscriptions subscriptions, WampTransport transport) {
		this.subscriptions = subscriptions;
		this.transport = transport;
	}

	@Override
	public void handle(EventMessage message) {
		WampInvocation invocation = new WampInvocation(message);
		subscriptions.callRegistredFn(message.getIdFunctionRegisted(), invocation);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof EventMessage;
	}

}
