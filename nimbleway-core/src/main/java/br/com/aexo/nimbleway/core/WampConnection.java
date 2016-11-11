package br.com.aexo.nimbleway.core;

import java.util.Iterator;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;

/**
 * interface especify a wamp connection
 * 
 * @author carlosr
 *
 */
public interface WampConnection {

	/**
	 * start comunication using a listed subprotocols
	 * 
	 * @param supportedSubProtocols
	 */
	void open(Iterator<SubProtocol> supportedSubProtocols);

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
	void onOpen(Consumer<WampTransport> onOpenCallback);

	/**
	 * handle exceptions
	 * 
	 * @param exceptionHandler
	 */
	void onException(Consumer<Exception> exceptionHandler);

}
