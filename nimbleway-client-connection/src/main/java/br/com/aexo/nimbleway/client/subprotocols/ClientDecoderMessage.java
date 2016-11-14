package br.com.aexo.nimbleway.client.subprotocols;

import br.com.aexo.nimbleway.client.messages.ClientMessage;

/**
 * aggregator interface for messages decoders
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface ClientDecoderMessage<T extends ClientMessage> {

	/**
	 * decode object for a wamp message
	 * 
	 * @param o
	 * @return
	 */
	T decode(Object o);

	/**
	 * 
	 * @param messageIdType
	 *            type of wamp message
	 * @return
	 */
	boolean isDecodeOf(Integer messageIdType);

}
