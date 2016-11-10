package br.com.aexo.nimbleway.core.subprotocols;

import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * aggregator interface for messages encoders
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface EncoderMessage<T extends WampMessage> {

	/**
	 * encode wamp message in custom format
	 * 
	 * @param message
	 * @return
	 */

	Object encode(T message);

	boolean isEncodeOf(WampMessage type);

}
