package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunction;
import br.com.aexo.nimbleway.storage.WampFunctions;

/**
 * handler responsible for recording functions for subsequent calls
 * 
 * @author carlosr
 *
 */
@Component
public class RegisterMessageHandler implements MessageHandler<RegisterMessage> {

	private WampFunctions functions;
	private WampTransport transport;

	public RegisterMessageHandler(WampFunctions functions,WampTransport transport) {
		this.functions = functions;
		this.transport = transport;
	}

	@Override
	public void handle(RegisterMessage message) {
		WampFunction function = new WampFunction(message.getId(), message.getName());
		function.onCallback(message.getFn());
		functions.waitRegistration(function);
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisterMessage;
	}

}
