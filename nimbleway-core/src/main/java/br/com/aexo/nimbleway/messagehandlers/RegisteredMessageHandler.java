package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.RegisteredMessage;

/**
 * copia do cache "esperando registro" para o cache de "registrado" a função
 * 
 * @author carlosr
 *
 */

public class RegisteredMessageHandler implements
		MessageHandler<RegisteredMessage> {

	@Override
	public void handle(RegisteredMessage message, MessageHandlerContext context) {
		Map<Long, RegisterMessage> wait = context
				.get(Context.WAIT_REGISTRATION_FUNCTION);
		if (wait == null) {
			wait = new HashMap<Long, RegisterMessage>();
			context.set(Context.WAIT_REGISTRATION_FUNCTION, wait);
		}

		Map<Long, RegisterMessage> registred = context
				.get(Context.REGISTERED_FUNCTION);
		if (registred == null) {
			registred = new HashMap<Long, RegisterMessage>();
			context.set(Context.REGISTERED_FUNCTION, registred);
		}

		RegisterMessage registerMessage = wait.get(message.getIdRequest());
		registred.put(message.getIdRegistration(), registerMessage);
		wait.remove(message.getIdRequest());
	}

}
