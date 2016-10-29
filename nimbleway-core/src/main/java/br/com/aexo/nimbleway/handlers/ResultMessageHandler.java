package br.com.aexo.nimbleway.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampResult;
import br.com.aexo.nimbleway.messages.ResultMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.storage.WampCalls;



/**
 * TODO possivel memory leak nas chamadas que não tem retorno, investigar.
 * 
 * 
 * @author carlosr
 *
 */

@Component
public class ResultMessageHandler implements MessageHandler<ResultMessage> {

	
	private WampCalls calls;

	public ResultMessageHandler(WampCalls calls) {
		this.calls = calls;
	}
	
	@Override
	public void handle(ResultMessage message) {
		calls.resolve(message.getIdCall(),new WampResult(message));
	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof ResultMessage;
	}

}
