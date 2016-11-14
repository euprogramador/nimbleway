package br.com.aexo.nimbleway.router.connection;

import java.util.function.Consumer;

public interface RouterTransport {
	/**
	 * callback usaged for received messages
	 * 
	 * @param fn
	 */
	void onRead(Consumer<RouterMessage> fn);

	/**
	 * write to transport a wamp message
	 * 
	 * @param wampMessage
	 */
	void write(RouterMessage wampMessage);

	/**
	 * close transport
	 */
	void close();
}
