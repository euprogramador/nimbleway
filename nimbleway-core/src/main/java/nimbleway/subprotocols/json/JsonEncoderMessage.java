package nimbleway.subprotocols.json;

import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.EncoderMessage;

public interface JsonEncoderMessage<T extends WampMessage> extends EncoderMessage<T> {
}
