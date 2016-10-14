package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.messages.RegisterMessage;

public class RegisterMessageHandler implements MessageHandler<RegisterMessage> {

	@Override
	public void handle(RegisterMessage message, MessageHandlerContext context) {
		Map<Long, RegisterMessage> wait = context
				.get(Context.WAIT_REGISTRATION_FUNCTION);
		if (wait == null) {
			wait = new HashMap<Long, RegisterMessage>();
			context.set(Context.WAIT_REGISTRATION_FUNCTION, wait);
		}
		wait.put(message.getId(), message);
	}

}
