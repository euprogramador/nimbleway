package br.com.aexo.nimbleway.router;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.HelloMessage;
import br.com.aexo.nimbleway.core.messages.SubscribeMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.router.core.HelloMessageHandler;
import br.com.aexo.nimbleway.router.core.MessageHandler;
import br.com.aexo.nimbleway.router.core.SubscribeMessageHandler;

public class WampRouter {

	private WampConnection connection;

	private Map<Class<? extends WampMessage>, MessageHandler<? extends WampMessage>> messageHandlers = new HashMap<>();

	public WampRouter(WampConnection connection) {
		this.connection = connection;
	}

	@SuppressWarnings("unchecked")
	public void start() {
		
		
		connection.onOpen((transport) -> {

			// configure message handlers
				messageHandlers.put(HelloMessage.class, new HelloMessageHandler(transport));
//				messageHandlers.put(SubscribeMessage.class, new SubscribeMessageHandler(transport));

				transport.onRead((msg) -> {

					MessageHandler<WampMessage> handler = (MessageHandler<WampMessage>) messageHandlers.get(msg.getClass());
					
					if (handler==null)
						throw new RuntimeException("nenhum handler encontrado para a mensagem:" + msg);
					
					handler.handle(msg, this);

				});
			});

		connection.open(Collections.emptyIterator());
	}

}
