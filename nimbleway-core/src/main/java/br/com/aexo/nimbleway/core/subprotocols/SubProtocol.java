package br.com.aexo.nimbleway.core.subprotocols;

import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * protocol used for message exchange between the client and the server
 * 
 * @author carlosr
 *
 */
public interface SubProtocol {

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
	Object encode(WampMessage message);

	/**
	 * decode message in wamp message
	 * 
	 * @param o
	 * @return
	 */
	WampMessage decode(Object o);


}
