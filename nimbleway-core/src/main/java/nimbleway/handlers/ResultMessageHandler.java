package nimbleway.handlers;

import nimbleway.WampCalls;
import nimbleway.WampResult;
import nimbleway.messages.ResultMessage;
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
