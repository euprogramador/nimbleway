package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampCall;
import br.com.aexo.nimbleway.storage.WampCalls;

/**
 * handle call messages, saving calls in temporary storage for future processing
 * 
 * @author carlosr
 *
 */
@Component
public class CallMessageHandler implements MessageHandler<CallMessage> {

	private WampCalls calls;
	private WampTransport transport;

	public CallMessageHandler(WampCalls calls, WampTransport transport) {
		this.calls = calls;
		this.transport = transport;
	}

	@Override
	public void handle(CallMessage message) {
		calls.registerCall(new WampCall(message.getId(), message.getDefered(), message.getPromise()));
		transport.write(message);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof CallMessage;
	}
}
