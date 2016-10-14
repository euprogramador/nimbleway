package br.com.aexo.nimbleway.subprotocols;

import br.com.aexo.nimbleway.DecoderMessage;
import br.com.aexo.nimbleway.EncoderMessage;
import br.com.aexo.nimbleway.SubProtocol;

public class JsonSubProtocol implements SubProtocol {

	@Override
	public String getName() {
		return "wamp.2.json";
	}

	// TODO tentar trabalhar com dependency Injection

	@Override
	public DecoderMessage getDecoder() {
		return new JsonDecoderMessage();
	}

	@Override
	public EncoderMessage getEncoder() {
		return new JsonEncoderMessage();
	}

}
