package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampError;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.DeferredWampMessage;
import br.com.aexo.nimbleway.messages.ReplyErrorMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle call messages, saving calls in temporary storage for future processing
 * 
 * @author carlosr
 *
 */
@Component
public class ReplyErrorMessageHandler implements MessageHandler<ReplyErrorMessage> {

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
