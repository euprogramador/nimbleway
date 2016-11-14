package br.com.aexo.nimbleway.client.connection;

import java.util.function.Consumer;

import br.com.aexo.nimbleway.client.messages.ClientMessage;

/**
 * encapsulates the communication with the connection becoming generic
 * communication
 * 
 * @author carlosr
 *
 */
public interface ClientTransport {

	/**
	 * callback usaged for received messages
	 * 
	 * @param fn
	 */
	void onRead(Consumer<ClientMessage> fn);

	/**
	 * write to transport a wamp message
	 * 
	 * @param wampMessage
	 */
	void write(ClientMessage wampMessage);

	/**
	 * close transport
	 */
	void close();

}
