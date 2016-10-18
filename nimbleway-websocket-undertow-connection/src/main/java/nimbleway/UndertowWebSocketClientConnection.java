package nimbleway;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import nimbleway.messages.WampMessage;
import nimbleway.subprotocols.SubProtocol;

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
	public void close() {
		try {
			webSocketChannel.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void open(Collection<SubProtocol> supportedSubProtocols) {
		try {
			
			Map<String,SubProtocol> subProtocols = new HashMap<>();
			supportedSubProtocols.forEach((subProtocol)->{subProtocols.put(subProtocol.getName(), subProtocol);});
			
			
			webSocketChannel = WebSocketClient.connectionBuilder(worker, bufferPool, uri)
					.setClientNegotiation(new WebSocketClientNegotiation(Arrays.asList(subProtocols.keySet().toArray(new String[]{})), Arrays.asList()))
					.connect().get();
			
			final SubProtocol subProtocol = subProtocols.get(webSocketChannel.getSubProtocol());
			
			// wrapper para fazer  o transporte
			WampTransport wampTransport = new WampTransport() {
				
				@Override
				public void write(WampMessage wampMessage) {
					Object msg = subProtocol.encode(wampMessage);
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
					WampMessage msg = subProtocol.decode(message); 
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

}
