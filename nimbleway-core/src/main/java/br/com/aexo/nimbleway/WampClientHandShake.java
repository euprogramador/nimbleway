package br.com.aexo.nimbleway;

import java.util.function.Consumer;

import br.com.aexo.nimbleway.messages.HelloMessage;
import br.com.aexo.nimbleway.messages.WelcomeMessage;

/**
 * classe responsável por fazer o handshake do client usando o protocol wamp
 */
public class WampClientHandShake {

	private WampTransport transport;
	private Consumer<WampSession> onHandShake;

	public WampClientHandShake(WampTransport transport) {
		this.transport = transport;
	}

	public void handshake(String reaml) {

		transport.onRead((message) -> {
			
			if (message instanceof WelcomeMessage) {
				// handshake concluded
				WampSession session = new WampSession(transport, reaml);
				onHandShake.accept(session);
			}
			
		});

		// envia uma mensagem inicial no handshake do protocolo do lado client
		transport.write(new HelloMessage(reaml));
	}

	public void onHandShake(Consumer<WampSession> onHandShake) {
		this.onHandShake = onHandShake;
	}

}
