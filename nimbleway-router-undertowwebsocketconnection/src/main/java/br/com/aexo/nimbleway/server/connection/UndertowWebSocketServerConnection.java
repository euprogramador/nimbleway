package br.com.aexo.nimbleway.server.connection;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.WebSocketProtocolHandshakeHandler;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedBinaryMessage;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.core.protocol.Handshake;
import io.undertow.websockets.core.protocol.version07.Hybi07Handshake;
import io.undertow.websockets.core.protocol.version08.Hybi08Handshake;
import io.undertow.websockets.core.protocol.version13.Hybi13Handshake;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.client.connection.WampTransport;
import br.com.aexo.nimbleway.client.messages.WampMessage;

public class UndertowWebSocketServerConnection {

	private Consumer<WampTransport> onOpen;
	private URI uri;
	private Undertow server;
	Consumer<WampMessage> transportOnReader;

	public UndertowWebSocketServerConnection(URI ws) {
		this.uri = ws;
	}

	public void open() {


		byte[] b = new byte[1024*4];
		new Random().nextBytes(b);
		String msg = new String(b);
		
		
		
		
		WebSocketConnectionCallback connectionCallback = new WebSocketConnectionCallback() {

			@Override
			public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {

				channel.getReceiveSetter().set(new AbstractReceiveListener() {

					public void decode(Object message) {
					
						
						WebSockets.sendText(msg, channel, null);
					}

					@Override
					protected void onFullBinaryMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
						decode(message.getData());
					}

					@Override
					protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
						decode(message.getData());
					}
				});

				channel.resumeReceives();

			}

		};

		String host = uri.getHost();
		Integer port = uri.getPort();
		String context = uri.getPath();

		Set<String> protocols = new HashSet<String>(Arrays.asList("wamp.2.json"));

		Set<Handshake> handshakes = new HashSet<>();
		handshakes.add(new Hybi13Handshake(protocols, true));
		handshakes.add(new Hybi08Handshake(protocols, true));
		handshakes.add(new Hybi07Handshake(protocols, true));

		server = Undertow.builder() //
				.setIoThreads(20)
				.setBufferSize(64000)
				.addHttpListener(port, host)
				//
				.setHandler(Handlers.path().addPrefixPath(context, new WebSocketProtocolHandshakeHandler(handshakes, connectionCallback))) //
				.build();

		server.start();

	}

	
	public static void main(String[] args) throws URISyntaxException {
		UndertowWebSocketServerConnection con = new UndertowWebSocketServerConnection(new URI("http://localhost:8080/ws"));
		con.open();
	}
	
}
