package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.WampInvocation;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.RegisterMessage;

public class InvocationMessageHandler implements MessageHandler<InvocationMessage> {

	@Override
	public void handle(InvocationMessage message, MessageHandlerContext context) {
		
		WampTransport transport = context.get(Context.TRANSPORT);
		
		Map<Long, RegisterMessage> registred = context.get(Context.REGISTERED_FUNCTION);
		if (registred == null) {
			registred = new HashMap<Long, RegisterMessage>();
			context.set(Context.REGISTERED_FUNCTION, registred);
		}

		RegisterMessage registerMessage = registred.get(message.getIdFunctionRegistration());
		WampInvocation call = new WampInvocation(message,transport);
		registerMessage.getFn().accept(call);
		
	}

}
