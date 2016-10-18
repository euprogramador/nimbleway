package nimbleway.subprotocols.json.decoder;

import nimbleway.messages.WelcomeMessage;
import nimbleway.subprotocols.json.JsonDecoderMessage;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessageJsonDecoder implements JsonDecoderMessage<WelcomeMessage> {

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(2).equals(messageIdType);
	}

	@Override
	public WelcomeMessage decode(Object o) {
		// TODO melhorar tratamento
		return new WelcomeMessage();
	}

}
