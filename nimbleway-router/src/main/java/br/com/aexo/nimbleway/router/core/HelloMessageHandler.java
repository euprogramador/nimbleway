package br.com.aexo.nimbleway.router.core;

import java.util.concurrent.ThreadLocalRandom;

import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.HelloMessage;
import br.com.aexo.nimbleway.core.messages.WelcomeMessage;
import br.com.aexo.nimbleway.router.WampRouter;

public class HelloMessageHandler implements MessageHandler<HelloMessage> {

	private WampTransport transport;

	public HelloMessageHandler(WampTransport transport) {
		this.transport = transport;
	}

	@Override
	public void handle(HelloMessage message, WampRouter router) {
		Long id = ThreadLocalRandom.current().nextLong(10000000, 99999999);

		String agent = "NimbleWay-<version>";
		WelcomeMessage msg = new WelcomeMessage(id,agent);
		transport.write(msg);
	}

}
