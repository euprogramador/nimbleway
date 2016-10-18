package nimbleway.handlers;

import nimbleway.WampFunctions;
import nimbleway.WampInvocation;
import nimbleway.WampTransport;
import nimbleway.messages.InvocationMessage;
import nimbleway.messages.WampMessage;

import org.springframework.stereotype.Component;




@Component
public class InvocationMessageHandler implements MessageHandler<InvocationMessage> {

	private WampTransport transport;
	private WampFunctions functions;

	public InvocationMessageHandler(WampTransport transport,WampFunctions functions) {
		this.transport = transport;
		this.functions = functions;
	}
	
	@Override
	public void handle(InvocationMessage message) {
		WampInvocation invocation = new WampInvocation(message,transport);
		functions.callRegistredFn(message.getIdFunctionRegisted(),invocation);
	}
	
	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof InvocationMessage;
	}


}
