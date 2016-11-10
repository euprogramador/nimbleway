package br.com.aexo.nimbleway.core.subprotocols.json.decoder;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.core.messages.GoodByeMessage;
import br.com.aexo.nimbleway.core.subprotocols.json.JsonDecoderMessage;

/**
 * Decoder of abort message
 * 
 * @author carlosr
 *
 */
@Component
class GoodByeMessageJsonDecoder implements JsonDecoderMessage<GoodByeMessage> {

	@Override
	public GoodByeMessage decode(Object o) {
		return new GoodByeMessage(false);
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(6).equals(messageIdType);
	}

}
