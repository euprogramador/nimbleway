package br.com.aexo.nimbleway.router.subprotocols.json;

import br.com.aexo.nimbleway.router.connection.RouterMessage;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterEncoderMessage;

public interface JsonEncoderMessage<T extends RouterMessage> extends RouterEncoderMessage<T> {
}
