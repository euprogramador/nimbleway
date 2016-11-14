package br.com.aexo.nimbleway.client.subprotocols;

import br.com.aexo.nimbleway.client.messages.ClientMessage;

/**
 * protocol used for message exchange between the client and the server
 * 
 * @author carlosr
 *
 */
public interface ClientSubProtocol {

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
	Object encode(ClientMessage message);

	/**
	 * decode message in wamp message
	 * 
	 * @param o
	 * @return
	 */
	ClientMessage decode(Object o);


}
