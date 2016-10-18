package nimbleway.handlers;


import nimbleway.WampFunctions;
import nimbleway.messages.RegisteredMessage;
import nimbleway.messages.WampMessage;

import org.springframework.stereotype.Component;

	

@Component
public class RegisteredMessageHandler implements MessageHandler<RegisteredMessage> {
	
	private WampFunctions functions;

	public RegisteredMessageHandler(WampFunctions functions) {
		this.functions = functions;
	}
	
	@Override
	public void handle(RegisteredMessage message) {
		functions.registryFunction(message.getIdRequest(),message.getIdRegistration());
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisteredMessage;
	}
}
