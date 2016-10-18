package nimbleway.handlers;

import nimbleway.WampCall;
import nimbleway.WampCalls;
import nimbleway.messages.CallMessage;
import nimbleway.messages.WampMessage;

import org.springframework.stereotype.Component;


/**
 * TODO possivel memory leak nas chamadas que não tem retorno, investigar.
 * 
 * 
 * @author carlosr
 *
 */

@Component
public class CallMessageHandler implements MessageHandler<CallMessage> {

	private WampCalls calls;
	
	public CallMessageHandler(WampCalls calls) {
		this.calls = calls;
	}
	
	@Override
	public void handle(CallMessage message) {
		calls.registerCall(new WampCall(message.getId(), message.getDefered(), message.getPromise()));
	}
	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof CallMessage;
	}
}
