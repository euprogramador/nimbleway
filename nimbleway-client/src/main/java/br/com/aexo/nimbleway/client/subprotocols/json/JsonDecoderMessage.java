package br.com.aexo.nimbleway.client.subprotocols.json;

import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.ClientDecoderMessage;

public interface JsonDecoderMessage<T extends ClientMessage> extends ClientDecoderMessage<T> {
}
