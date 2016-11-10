package br.com.aexo.nimbleway.core.subprotocols.json;

import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.DecoderMessage;

public interface JsonDecoderMessage<T extends WampMessage> extends DecoderMessage<T> {
}
