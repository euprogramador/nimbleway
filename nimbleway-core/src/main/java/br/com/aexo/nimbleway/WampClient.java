package br.com.aexo.nimbleway;

import java.util.function.Consumer;

public class WampClient {

	private WampConnection conn;
	private Consumer<WampSession> onOpen;

	public WampClient(WampConnection conn) {
		this.conn = conn;
	}

	public void onOpen(Consumer<WampSession> fn) {
		this.onOpen = fn;
	}

	public void open(String reaml) {
		conn.onOpen((transport) -> {
			WampClientHandShake handshake = new WampClientHandShake(transport);
			handshake.onHandShake((session) -> {
				onOpen.accept(session);
			});
			handshake.handshake(reaml);
		});
		conn.open();
	}

	public void close() {
		conn.close();
	}

}
