package br.com.aexo.nimbleway;

import java.util.function.Consumer;

public interface WampTransport {
	
	void onRead(Consumer<WampMessage> fn);

	void write(WampMessage wampMessage);


}
