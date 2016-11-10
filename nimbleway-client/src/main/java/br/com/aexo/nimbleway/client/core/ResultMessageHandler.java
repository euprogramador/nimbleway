package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.MessageType;
import br.com.aexo.nimbleway.core.ResultCall;
import br.com.aexo.nimbleway.core.messages.CallMessage;
import br.com.aexo.nimbleway.core.messages.ResultMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

@Component
class ResultMessageHandler implements MessageHandler<ResultMessage> {

	
	private WaitBrokerCommunication wait;

	public ResultMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}
	
	@Override
	public void handle(ResultMessage message,ClientSession session) {
		CallMessage callMessage = wait.retrieve(new BrokerCommunicationKey(MessageType.CALL,message.getCallId()));
		callMessage.resolve(new ResultCall(message));
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof ResultMessage;
	}

}
