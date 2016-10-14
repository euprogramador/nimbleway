package br.com.aexo.nimbleway.messagehandlers;

import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.RegisteredMessage;
import br.com.aexo.nimbleway.messages.ResultMessage;

public class MessageHandlers {

	MessageHandlerContext context = new MessageHandlerContext();

	Map<Class<? extends WampMessage>, MessageHandler<?>> handlers = new HashMap<>();
	
	public MessageHandlers(){
	}
	

	public MessageHandlers(WampTransport transport, String realm) {
		context.set(Context.REALM,realm);
		context.set(Context.TRANSPORT,transport);
		
		handlers.put(RegisterMessage.class, new RegisterMessageHandler());
		handlers.put(RegisteredMessage.class, new RegisteredMessageHandler());
		handlers.put(CallMessage.class, new CallMessageHandler());
		handlers.put(InvocationMessage.class, new InvocationMessageHandler());
		handlers.put(ResultMessage.class, new ResultMessageHandler());
	}


	public void handle(WampMessage message) {
		
		MessageHandler handler = handlers.get(message.getClass());
		
		if (handler == null) {
			System.out.println("nenhum handler encontrado: " + message);
		}

		handler.handle(message, context);
		
	}

}
