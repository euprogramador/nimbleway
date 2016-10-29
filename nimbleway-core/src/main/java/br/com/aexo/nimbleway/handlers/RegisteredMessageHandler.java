package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.RegisteredMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunctions;

/**
 * handle RegistredMessage recording function in session
 * 
 * @author carlosr
 *
 */
@Component
public class RegisteredMessageHandler implements MessageHandler<RegisteredMessage> {

	private WampFunctions functions;

	public RegisteredMessageHandler(WampFunctions functions) {
		this.functions = functions;
	}

	@Override
	public void handle(RegisteredMessage message) {
		functions.registryFunction(message.getIdRequest(), message.getIdRegistration());
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisteredMessage;
	}
}
