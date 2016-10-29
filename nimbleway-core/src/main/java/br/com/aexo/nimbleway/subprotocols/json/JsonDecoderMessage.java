package br.com.aexo.nimbleway.subprotocols.json;

import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.subprotocols.DecoderMessage;

public interface JsonDecoderMessage<T extends WampMessage> extends DecoderMessage<T> {
}
