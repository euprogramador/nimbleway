package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.WampError;
import br.com.aexo.nimbleway.core.messages.DeferredWampMessage;
import br.com.aexo.nimbleway.core.messages.ReplyErrorMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle call messages, saving calls in temporary storage for future processing
 * 
 * @author carlosr
 *
 */
@Component
class ReplyErrorMessageHandler implements MessageHandler<ReplyErrorMessage> {

	private WaitBrokerCommunication wait;

	public ReplyErrorMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}

	@Override
	public void handle(ReplyErrorMessage message, ClientSession session) {
		DeferredWampMessage<? extends Object, WampError> defMessage = wait.retrieve(new BrokerCommunicationKey(message.getType(), message.getRequestId()));
		WampError rejection = new WampError(message);
		defMessage.reject(rejection);
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof ReplyErrorMessage;
	}
}
