package br.com.aexo.nimbleway.client.handlers;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.WampTransport;
import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.messages.GoodByeMessage;
import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * handle goodbyeMessage origin from router or client
 * 
 * @author carlosr
 *
 */
@Component
public class GoodByeMessageHandler implements MessageHandler<GoodByeMessage> {

	private WampTransport transport;

	private boolean initFromClient;
	
	public GoodByeMessageHandler(WampTransport transport) {
		this.transport = transport;
	}

	@Override
	public void handle(GoodByeMessage msg,ClientSession session) {

		// goodbye initialized from client
		if (msg.isWaitReplyRouter()) {
			initFromClient = true;
			transport.write(msg);
		} else if (initFromClient) {
			// reply goodbye message
//			transport.write(new GoodByeMessage(false));
			transport.close();
		} else {
			transport.write(new GoodByeMessage(false));
			transport.close();
		}

	}

	@Override
	public boolean isHandleOf(WampMessage msg) {
		return msg instanceof GoodByeMessage;
	}

}
