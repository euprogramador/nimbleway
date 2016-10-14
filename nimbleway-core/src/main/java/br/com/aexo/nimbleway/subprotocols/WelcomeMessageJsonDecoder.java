package br.com.aexo.nimbleway.subprotocols;

import br.com.aexo.nimbleway.DecoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.WelcomeMessage;

public class WelcomeMessageJsonDecoder implements DecoderMessage {

	@Override
	public WampMessage decode(Object o) {
		// TODO melhorar para trazer os dados que vem do servidor para identificar melhor 
		return new WelcomeMessage();
	}

}
