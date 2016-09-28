package nimbleway;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.WebSocketProtocolHandshakeHandler;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.protocol.Handshake;
import io.undertow.websockets.core.protocol.version07.Hybi07Handshake;
import io.undertow.websockets.core.protocol.version08.Hybi08Handshake;
import io.undertow.websockets.core.protocol.version13.Hybi13Handshake;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class UndertowWebSocketServerConnection implements ServerConnection {

	private URI uri;
	private Map<String, MessageFormat> supportedSubProtocols;
	private Observable<WampSession> onListen;

	private Undertow server;
	private Subscriber<? super WampSession> onListenObserver;

	public UndertowWebSocketServerConnection(String uri, Map<String, MessageFormat> supportedSubProtocols) {
		this.supportedSubProtocols = supportedSubProtocols;
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		// cria o observer e expoe o subscriber
		onListen = Observable.create((observable) -> {
			onListenObserver = observable;
		});
		onListen = onListen.observeOn(Schedulers.computation());
		onListen.subscribe();
	}

	@Override
	public void listen() {

		WebSocketConnectionCallback connectionCallback = new WebSocketConnectionCallback() {

			@Override
			public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
				MessageFormat subProtocol = supportedSubProtocols.get(channel.getSubProtocol());

				WampSession wampSession = new UndertowWampSession(channel, subProtocol);
				onListenObserver.onNext(wampSession);
			}

		};
		String host = uri.getHost();
		Integer port = uri.getPort();
		String context = uri.getPath();

		Set<Handshake> handshakes = new HashSet<>();
		handshakes.add(new Hybi13Handshake(supportedSubProtocols.keySet(), true));
		handshakes.add(new Hybi08Handshake(supportedSubProtocols.keySet(), true));
		handshakes.add(new Hybi07Handshake(supportedSubProtocols.keySet(), true));

		server = Undertow.builder() //
				.addHttpListener(port, host) //
				.setHandler(Handlers.path().addPrefixPath(context, new WebSocketProtocolHandshakeHandler(handshakes, connectionCallback))) //
				.build();

		server.start();
	}

	@Override
	public Observable<WampSession> onListen() {
		return onListen;
	}

}
