package br.com.aexo.nimbleway.client.connection;

import java.util.Iterator;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.client.subprotocols.ClientSubProtocol;

/**
 * interface especify a wamp connection
 * 
 * @author carlosr
 *
 */
public interface ClientConnection {

	/**
	 * start comunication using a listed subprotocols
	 * 
	 * @param supportedSubProtocols
	 */
	void open(Iterator<ClientSubProtocol> supportedSubProtocols);

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
	void onOpen(Consumer<ClientTransport> onOpenCallback);

	/**
	 * handle exceptions
	 * 
	 * @param exceptionHandler
	 */
	void onException(Consumer<Exception> exceptionHandler);

}
