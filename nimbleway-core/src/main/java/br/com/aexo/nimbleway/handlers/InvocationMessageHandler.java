package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampInvocationResult;
import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.messages.InvocationMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampFunctions;

/**
 * handle invocationMessage calling registred function
 * 
 * @author carlosr
 *
 */

@Component
public class InvocationMessageHandler implements MessageHandler<InvocationMessage> {

	private WampTransport transport;
	private WampFunctions functions;

	public InvocationMessageHandler(WampTransport transport, WampFunctions functions) {
		this.transport = transport;
		this.functions = functions;
	}

	@Override
	public void handle(InvocationMessage message) {
		WampInvocationResult invocation = new WampInvocationResult(message, transport);
		functions.callRegistredFn(message.getIdFunctionRegisted(), invocation);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof InvocationMessage;
	}

}
