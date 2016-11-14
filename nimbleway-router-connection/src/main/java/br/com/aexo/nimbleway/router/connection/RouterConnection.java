package br.com.aexo.nimbleway.router.connection;

import java.util.Iterator;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.router.connection.subprotocols.RouterSubProtocol;

public interface RouterConnection {

	/**
	 * start comunication using a listed subprotocols
	 * 
	 * @param supportedSubProtocols
	 */
	void open(Iterator<RouterSubProtocol> supportedSubProtocols);

	/**
	 * close this connection
	 */
	void close();

	/**
	 * callback called for a conclude connection, connection is encapsulated in
	 * wamp transport for read and write messages
	 * 
	 * @param onOpenCallback
	 */
	void onOpen(Consumer<RouterTransport> onOpenCallback);

	/**
	 * handle exceptions
	 * 
	 * @param exceptionHandler
	 */
	void onException(Consumer<Exception> exceptionHandler);
}
