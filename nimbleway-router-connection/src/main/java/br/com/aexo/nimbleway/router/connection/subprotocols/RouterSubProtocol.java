package br.com.aexo.nimbleway.router.connection.subprotocols;

import br.com.aexo.nimbleway.router.connection.RouterMessage;

public interface RouterSubProtocol {

	/**
	 * Name of protocol
	 * 
	 * @return
	 */
	String getName();

	/**
	 * encode wamp message in especific format
	 * 
	 * @param message
	 * @return
	 */
	Object encode(RouterMessage message);

	/**
	 * decode message in wamp message
	 * 
	 * @param o
	 * @return
	 */
	RouterMessage decode(Object o);

}
