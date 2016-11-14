package br.com.aexo.nimbleway.router.connection.subprotocols;

import br.com.aexo.nimbleway.router.connection.RouterMessage;

/**
 * aggregator interface for messages encoders
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface RouterEncoderMessage<T extends RouterMessage> {

	/**
	 * encode wamp message in custom format
	 * 
	 * @param message
	 * @return
	 */

	Object encode(T message);

	boolean isEncodeOf(RouterMessage type);

}
