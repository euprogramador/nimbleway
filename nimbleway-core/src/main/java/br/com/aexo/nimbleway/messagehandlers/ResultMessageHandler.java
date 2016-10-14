package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.WampResult;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.ResultMessage;

/**
 * TODO possivel memory leak nas chamadas que não tem retorno, investigar.
 * 
 * 
 * @author carlosr
 *
 */

public class ResultMessageHandler implements MessageHandler<ResultMessage> {

	@Override
	public void handle(ResultMessage message, MessageHandlerContext context) {

		Map<Long, CallMessage> wait = context.get(Context.WAIT_CALL);
		if (wait == null) {
			wait = new HashMap<Long, CallMessage>();
			context.set(Context.WAIT_CALL, wait);
		}
		CallMessage call = wait.get(message.getIdCall());
		call.getDefered().resolve(new WampResult(message));
	}

}
