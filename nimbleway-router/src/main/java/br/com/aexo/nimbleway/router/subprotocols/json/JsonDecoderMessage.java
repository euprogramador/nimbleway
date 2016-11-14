package br.com.aexo.nimbleway.router.subprotocols.json;

import br.com.aexo.nimbleway.router.connection.RouterMessage;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterDecoderMessage;

public interface JsonDecoderMessage<T extends RouterMessage> extends RouterDecoderMessage<T> {
}
