package br.com.aexo.nimbleway.subprotocols;

import br.com.aexo.nimbleway.messages.WampMessage;

/**
 * aggregator interface for messages decoders
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface DecoderMessage<T extends WampMessage> {

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
