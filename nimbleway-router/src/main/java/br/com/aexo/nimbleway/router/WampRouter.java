package br.com.aexo.nimbleway.router;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.messages.HelloMessage;
import br.com.aexo.nimbleway.core.messages.RegisterMessage;
import br.com.aexo.nimbleway.core.messages.RegisteredMessage;
import br.com.aexo.nimbleway.core.messages.WelcomeMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;

public class WampRouter {

	private WampConnection connection;

	private Consumer<Exception> exceptionHandler;
	private Registrations registrations = new Registrations();

	public WampRouter(WampConnection connection) {
		this.connection = connection;
	}

	public void onExceptionHandler(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public void start() {

		ServiceLoader<SubProtocol> subProtocols = ServiceLoader.load(SubProtocol.class);
		connection.onException(exceptionHandler);

		connection.onOpen((transport) -> {
			// / executado sempre que um client estabelece conexão com o router

				// configura o onRead no transporte
				transport.onRead((msg) -> {

					if (msg instanceof HelloMessage) {
						long sessionId = randomId();
						transport.write(new WelcomeMessage(sessionId, "nimbleWay"));
					} else if (msg instanceof RegisterMessage) {
						long id = randomId();
						registrations.save(id, transport, ((RegisterMessage) msg).getRegistration());
						RegisteredMessage resgistered = new RegisteredMessage(((RegisterMessage) msg).getId(), randomId());
						transport.write(resgistered);
					}

				});

			});

		// init router variables

		connection.open(subProtocols.iterator());
	}

	private long randomId() {
		return ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
	}
}
