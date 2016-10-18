package nimbleway;

import java.util.function.Consumer;

import nimbleway.messages.HelloMessage;
import nimbleway.messages.WelcomeMessage;

import org.springframework.stereotype.Component;


@Component
public class WampClientHandshake {

	private WampTransport transport;
	private Consumer<WampSession> onHandshakeCallback;
	private WampSession session;

	public WampClientHandshake(WampTransport transport, WampSession session) {
		this.transport = transport;
		this.session = session;
	}

	public void handshake(String realm) {

		this.transport.onRead((message) -> {
			// TODO adicionar suporte para outras mensagens de erro no handshake
				if (message instanceof WelcomeMessage) {
					// success of handshake
					session.configureHandlersInTransport();
					onHandshakeCallback.accept(session);
				}
			});
		
		// initHandshakeProccess
		transport.write(new HelloMessage(realm));
	}

	public void onHandshake(Consumer<WampSession> onHandshakeCallback) {
		this.onHandshakeCallback = onHandshakeCallback;
	}
}
