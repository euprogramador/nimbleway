package br.com.aexo.nimbleway.subprotocols.json;

import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.subprotocols.EncoderMessage;

public interface JsonEncoderMessage<T extends WampMessage> extends EncoderMessage<T> {
}
