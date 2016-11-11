package br.com.aexo.nimbleway.core.subprotocols.json;

import br.com.aexo.nimbleway.core.messages.GoodByeMessage;

/**
 * Decoder of abort message
 * 
 * @author carlosr
 *
 */
class GoodByeMessageJsonDecoder implements JsonDecoderMessage<GoodByeMessage> {

	@Override
	public GoodByeMessage decode(Object o) {

		// TODO melhorar o decoder do goodbye

		return new GoodByeMessage();
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(6).equals(messageIdType);
	}

}