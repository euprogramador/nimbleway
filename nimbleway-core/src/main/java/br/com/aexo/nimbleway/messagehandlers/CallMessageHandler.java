package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.messages.CallMessage;

/**
 * TODO possivel memory leak nas chamadas que não tem retorno, investigar.
 * 
 * 
 * @author carlosr
 *
 */

public class CallMessageHandler implements MessageHandler<CallMessage> {

	@Override
	public void handle(CallMessage message, MessageHandlerContext context) {

		Map<Long, CallMessage> wait = context.get(Context.WAIT_CALL);
		if (wait == null) {
			wait = new HashMap<Long, CallMessage>();
			context.set(Context.WAIT_CALL, wait);
		}
		wait.put(message.getId(), message);
	}

}
