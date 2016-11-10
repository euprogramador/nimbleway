package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.ResultCall;
import br.com.aexo.nimbleway.TypeMessage;
import br.com.aexo.nimbleway.client.BrokerCommunicationKey;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.client.WaitBrokerCommunication;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.ResultMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

@Component
public class ResultMessageHandler implements MessageHandler<ResultMessage> {

	
	private WaitBrokerCommunication wait;

	public ResultMessageHandler(WaitBrokerCommunication wait) {
		this.wait = wait;
	}
	
	@Override
	public void handle(ResultMessage message,ClientSession session) {
		CallMessage callMessage = wait.retrieve(new BrokerCommunicationKey(TypeMessage.CALL,message.getCallId()));
		callMessage.resolve(new ResultCall(message));
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof ResultMessage;
	}

}
