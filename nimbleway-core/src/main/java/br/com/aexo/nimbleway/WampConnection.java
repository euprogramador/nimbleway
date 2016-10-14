package br.com.aexo.nimbleway;

import java.util.function.Consumer;

public interface WampConnection {

	void onOpen(Consumer<WampTransport> fn);
	
	void open(); // efetua o handshake do protocolo

	void close();


}
