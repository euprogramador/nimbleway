package br.com.aexo.nimbleway.client.subprotocols.json;

import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.ClientEncoderMessage;

public interface JsonEncoderMessage<T extends ClientMessage> extends ClientEncoderMessage<T> {
}
