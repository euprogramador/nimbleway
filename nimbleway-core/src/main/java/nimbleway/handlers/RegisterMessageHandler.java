package nimbleway.handlers;

import nimbleway.WampFunction;
import nimbleway.WampFunctions;
import nimbleway.messages.RegisterMessage;
import nimbleway.messages.WampMessage;

import org.springframework.stereotype.Component;

@Component
public class RegisterMessageHandler implements MessageHandler<RegisterMessage> {

	private WampFunctions functions;

	public RegisterMessageHandler(WampFunctions functions) {
		this.functions = functions;
	}

	@Override
	public void handle(RegisterMessage message) {
		WampFunction function = new WampFunction(message.getId(),message.getName());
		function.onCallback(message.getFn());
		functions.waitRegistration(function);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof RegisterMessage;
	}

}
