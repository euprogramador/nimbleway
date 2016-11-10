package br.com.aexo.nimbleway.client.core;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.GoodByeMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * handle goodbyeMessage origin from router or client
 * 
 * @author carlosr
 *
 */
@Component
class GoodByeMessageHandler implements MessageHandler<GoodByeMessage> {

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
