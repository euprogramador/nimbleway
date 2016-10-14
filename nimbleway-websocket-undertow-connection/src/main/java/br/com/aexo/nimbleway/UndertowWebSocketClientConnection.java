package br.com.aexo.nimbleway;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.client.WebSocketClient;
import io.undertow.websockets.client.WebSocketClientNegotiation;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedBinaryMessage;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.function.Consumer;

import org.xnio.XnioWorker;

public class UndertowWebSocketClientConnection implements WampConnection {

	private Consumer<WampTransport> onOpen;
	private URI uri;
	Consumer<WampMessage> transportOnReader;
	private XnioWorker worker;
	private DefaultByteBufferPool bufferPool;
	private WebSocketChannel webSocketChannel;


	public UndertowWebSocketClientConnection(XnioWorker worker, DefaultByteBufferPool bufferPool, URI ws) {
		this.worker = worker;
		this.bufferPool = bufferPool;
		this.uri = ws;
	}

	@Override
	public void onOpen(Consumer<WampTransport> fn) {
		this.onOpen = fn;
	}

	@Override
	public void open() {
		try {
			webSocketChannel = WebSocketClient.connectionBuilder(worker, bufferPool, uri)
					.setClientNegotiation(new WebSocketClientNegotiation(Arrays.asList("wamp.2.json"), Arrays.asList()))
					.connect().get();
			
			SubProtocols subProtocols = new SubProtocols();
			final SubProtocol subProtocol = subProtocols.getSubProtocol(webSocketChannel.getSubProtocol());
			
			// wrapper para fazer  o transporte
			WampTransport wampTransport = new WampTransport() {
				
				@Override
				public void write(WampMessage wampMessage) {
					Object msg = subProtocol.getEncoder().encode(wampMessage);
					System.out.println("<<< " + msg);
					WebSockets.sendText(msg.toString(), webSocketChannel,null);
				}
				
				@Override
				public void onRead(Consumer<WampMessage> fn) {
					transportOnReader = fn;
				}

				
			};
			
			// adiciona o receiver
			webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
				public void decode(Object message) {
					System.out.println(">>> " + message);
					WampMessage msg = subProtocol.getDecoder().decode(message); 
					transportOnReader.accept(msg);
				}
	
				@Override
				protected void onFullBinaryMessage( WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
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
			throw new RuntimeException(e);
		}
		
	}
	@Override
	public void close() {
		try {
			webSocketChannel.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
