package br.com.aexo.nimbleway.client.subprotocols.json.decoder;

import br.com.aexo.nimbleway.client.messages.GoodByeMessage;
import br.com.aexo.nimbleway.client.subprotocols.json.JsonDecoderMessage;

/**
 * Decoder of abort message
 * 
 * @author carlosr
 *
 */
public class GoodByeMessageJsonDecoder implements JsonDecoderMessage<GoodByeMessage> {

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
