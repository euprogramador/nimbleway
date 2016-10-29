package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampInvocation;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.SubscribeMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunction;
import br.com.aexo.nimbleway.storage.WampSubscriptions;

/**
 * handler responsible for recording functions for subsequent calls
 * 
 * @author carlosr
 *
 */
@Component
public class SubscribeMessageHandler implements MessageHandler<SubscribeMessage> {

	private WampTransport transport;
	private WampSubscriptions subscriptions;

	public SubscribeMessageHandler(WampSubscriptions subscriptions,WampTransport transport) {
		this.subscriptions = subscriptions;
		this.transport = transport;
	}

	@Override
	public void handle(SubscribeMessage message) {
		WampFunction<WampInvocation> function = new WampFunction<WampInvocation>(message.getId(), message.getTopic());
		function.onCallback(message.getFn());
		subscriptions.waitRegistration(function);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof SubscribeMessage;
	}

}
