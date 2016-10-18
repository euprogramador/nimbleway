package nimbleway;

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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.SubProtocol;

public class UndertowWebSocketServerConnection implements WampConnection {

	private Consumer<WampTransport> onOpen;
	private URI uri;
	private Undertow server;
	Consumer<WampMessage> transportOnReader;

	public UndertowWebSocketServerConnection(URI ws) {
		this.uri = ws;
	}

	@Override
	public void onOpen(Consumer<WampTransport> fn) {
		this.onOpen = fn;
	}

	@Override
	public void open() {

		SubProtocols subProtocols = new SubProtocols();

		WebSocketConnectionCallback connectionCallback = new WebSocketConnectionCallback() {

			@Override
			public void onConnect(WebSocketHttpExchange exchange,
					WebSocketChannel channel) {

				final SubProtocol subProtocol = subProtocols
						.getSubProtocol(channel.getSubProtocol());

				WampTransport wampTransport = new WampTransport() {

					@Override
					public void write(WampMessage wampMessage) {
						Object msg = subProtocol.getEncoder().encode(
								wampMessage);
						WebSockets.sendText(msg.toString(), channel, null);
					}

					@Override
					public void onRead(Consumer<WampMessage> fn) {
						transportOnReader = fn;
					}

				};

				channel.getReceiveSetter().set(new AbstractReceiveListener() {

					public void decode(Object message) {
						WampMessage msg = subProtocol.getDecoder().decode(
								message);
						transportOnReader.accept(msg);
					}

					@Override
					protected void onFullBinaryMessage(
							WebSocketChannel channel,
							BufferedBinaryMessage message) throws IOException {
						decode(message.getData());
					}

					@Override
					protected void onFullTextMessage(WebSocketChannel channel,
							BufferedTextMessage message) {
						decode(message.getData());
					}
				});

				channel.resumeReceives();

				onOpen.accept(wampTransport);
			}

		};

		String host = uri.getHost();
		Integer port = uri.getPort();
		String context = uri.getPath();

		Set<Handshake> handshakes = new HashSet<>();
		handshakes.add(new Hybi13Handshake(subProtocols.supportedProtocols(),
				true));
		handshakes.add(new Hybi08Handshake(subProtocols.supportedProtocols(),
				true));
		handshakes.add(new Hybi07Handshake(subProtocols.supportedProtocols(),
				true));

		server = Undertow.builder() //
				.addHttpListener(port, host)
				//
				.setHandler(
						Handlers.path().addPrefixPath(
								context,
								new WebSocketProtocolHandshakeHandler(
										handshakes, connectionCallback))) //
				.build();

		server.start();

	}

	@Override
	public void close() {
		server.stop();
	}

	@Override
	public void open(Collection<SubProtocol> supportedSubProtocols) {
		// TODO Auto-generated method stub
		
	}

}
