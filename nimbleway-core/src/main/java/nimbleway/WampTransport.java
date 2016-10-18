package nimbleway;
 
import java.util.function.Consumer;

import nimbleway.messages.WampMessage;


public interface WampTransport {

	void onRead(Consumer<WampMessage> fn);

	void write(WampMessage wampMessage);

}
