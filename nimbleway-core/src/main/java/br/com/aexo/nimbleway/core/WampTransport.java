package br.com.aexo.nimbleway.core;

import java.util.function.Consumer;

import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * encapsulates the communication with the connection becoming generic
 * communication
 * 
 * @author carlosr
 *
 */
public interface WampTransport {

	/**
	 * callback usaged for received messages
	 * 
	 * @param fn
	 */
	void onRead(Consumer<WampMessage> fn);

	/**
	 * write to transport a wamp message
	 * 
	 * @param wampMessage
	 */
	void write(WampMessage wampMessage);

	/**
	 * close transport
	 */
	void close();

}
