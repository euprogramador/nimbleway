package br.com.aexo.nimbleway.client.subprotocols;

import br.com.aexo.nimbleway.client.messages.ClientMessage;

/**
 * aggregator interface for messages encoders
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface ClientEncoderMessage<T extends ClientMessage> {

	/**
	 * encode wamp message in custom format
	 * 
	 * @param message
	 * @return
	 */

	Object encode(T message);

	boolean isEncodeOf(ClientMessage type);

}
