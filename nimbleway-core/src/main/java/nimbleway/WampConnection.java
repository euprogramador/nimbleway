package nimbleway;

import java.util.Collection;
import java.util.function.Consumer;

import nimbleway.subprotocols.SubProtocol;


public interface WampConnection {
	
	void open(Collection<SubProtocol> supportedSubProtocols);

	void close();
	
	void onOpen(Consumer<WampTransport> onOpenCallback);

}
