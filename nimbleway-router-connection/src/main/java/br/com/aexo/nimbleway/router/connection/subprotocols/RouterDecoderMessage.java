package br.com.aexo.nimbleway.router.connection.subprotocols;

import br.com.aexo.nimbleway.router.connection.RouterMessage;

/**
 * aggregator interface for messages decoders
 * 
 * @author carlosr
 *
 * @param <T>
 */
public interface RouterDecoderMessage<T extends RouterMessage> {

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
