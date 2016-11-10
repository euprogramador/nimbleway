package br.com.aexo.nimbleway.core.subprotocols.json;

import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.EncoderMessage;

public interface JsonEncoderMessage<T extends WampMessage> extends EncoderMessage<T> {
}
