package nimbleway.subprotocols.json;

import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.DecoderMessage;

public interface JsonDecoderMessage<T extends WampMessage> extends DecoderMessage<T> {
}
