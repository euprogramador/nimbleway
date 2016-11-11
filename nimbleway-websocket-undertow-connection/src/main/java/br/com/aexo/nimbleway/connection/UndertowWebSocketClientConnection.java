package br.com.aexo.nimbleway.connection;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.XnioWorker;

import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.client.WebSocketClient;
import io.undertow.websockets.client.WebSocketClientNegotiation;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedBinaryMessage;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.StreamSourceFrameChannel;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;

public class UndertowWebSocketClientConnection implements WampConnection {

	private static Logger log = LoggerFactory.getLogger(UndertowWebSocketClientConnection.class);

	private Consumer<WampTransport> onOpen;
	private URI uri;
	Consumer<WampMessage> transportOnReader;
	private XnioWorker worker;
	private DefaultByteBufferPool bufferPool;
	private WebSocketChannel webSocketChannel;
	private Consumer<Exception> exceptionHandler;

	private ExecutorService executorService;

	public UndertowWebSocketClientConnection(XnioWorker worker, DefaultByteBufferPool bufferPool, URI ws, ExecutorService executorService) {
		this.worker = worker;
		this.bufferPool = bufferPool;
		this.uri = ws;
		this.executorService = executorService;
	}

	@Override
	public void onOpen(Consumer<WampTransport> fn) {
		log.trace("configured on open handler");
		this.onOpen = fn;
	}

	@Override
	public void close() {
		try {
			webSocketChannel.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void open(Iterator<SubProtocol> supportedSubProtocols) {
		try {
			log.debug("open connection");
			Map<String, SubProtocol> subProtocols = new HashMap<>();
			supportedSubProtocols.forEachRemaining((subProtocol) -> {
				subProtocols.put(subProtocol.getName(), subProtocol);
			});

			log.trace("suported subprotocols: " + Arrays.asList(subProtocols.keySet().toArray(new String[] {})));

			webSocketChannel = WebSocketClient.connectionBuilder(worker, bufferPool, uri).setClientNegotiation(new WebSocketClientNegotiation(Arrays.asList(subProtocols.keySet().toArray(new String[] {})), Arrays.asList())).connect().get();

			// webSocketChannel.getCloseSetter().set(new
			// ChannelListener<Channel>() {
			//
			// @Override
			// public void handleEvent(Channel channel) {
			// worker.shutdown();
			// }
			// });

			final SubProtocol subProtocol = subProtocols.get(webSocketChannel.getSubProtocol());

			log.trace("subprotocol usaged: " + subProtocol.getName());

			// wrapper para fazer o transporte
			WampTransport wampTransport = new WampTransport() {

				@Override
				public void write(WampMessage wampMessage) {
					try {
						log.debug(">>> write message: " + wampMessage);
						Object msg = subProtocol.encode(wampMessage);
						log.trace(">>> write in connection: " + msg);
						WebSockets.sendText(msg.toString(), webSocketChannel, null);
					} catch (Exception e) {
						exceptionHandler.accept(e);
					}
				}

				@Override
				public void onRead(Consumer<WampMessage> fn) {
					log.debug("configure message reader");
					transportOnReader = fn;
				}

				@Override
				public void close() {
					try {
						log.debug("transport closing");
						worker.shutdown();
						webSocketChannel.close();
					} catch (IOException e) {
						log.error("occurred error", e);
						exceptionHandler.accept(e);
					}
				}

			};

			// adiciona o receiver
			webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {

				@Override
				protected void onClose(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
					System.out.println("sss");
					super.onClose(webSocketChannel, channel);
				}

				public void decode(Object message) {
					try {

						executorService.submit(() -> {
							try {
								log.trace("<<< raw message received: " + message);
								WampMessage msg = subProtocol.decode(message);
								log.debug("<<< wamp message receved: " + msg);
								transportOnReader.accept(msg);
							} catch (Exception e) {
								exceptionHandler.accept(e);
							}
						});

					} catch (Exception e) {
						exceptionHandler.accept(e);
					}
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

			webSocketChannel.resumeReceives();

			onOpen.accept(wampTransport);
		} catch (Exception e) {
			log.error("occurred error", e);
			exceptionHandler.accept(e);
		}

	}

	@Override
	public void onException(Consumer<Exception> exceptionHandler) {
		log.trace("configured on exception handler");
		this.exceptionHandler = exceptionHandler;

	}

}
