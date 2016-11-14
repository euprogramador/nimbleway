import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;

public class InVMServerConnection implements WampConnection {

	private static Logger log = LoggerFactory.getLogger(InVMServerConnection.class) ;
	
	private Iterator<SubProtocol> supportedSubProtocols;
	private Consumer<WampTransport> onOpenCallback;
	private Consumer<Exception> exceptionHandler;
	private String protocolName;
	private SubProtocol protocol;

	private ExecutorService pool = Executors.newCachedThreadPool();

	public InVMServerConnection() {
	}

	public InVMServerConnection(String protocolName) {
		this.protocolName = protocolName;
	}

	@Override
	public void open(Iterator<SubProtocol> supportedSubProtocols) {
		this.supportedSubProtocols = supportedSubProtocols;

		supportedSubProtocols.forEachRemaining((p) -> {
			if (p.getName().equals(protocolName))
				protocol = p;
		});
	}

	@Override
	public void close() {
	}

	@Override
	public void onOpen(Consumer<WampTransport> onOpenCallback) {
		this.onOpenCallback = onOpenCallback;
	}

	@Override
	public void onException(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public WampConnection newClientConnection() {

		InVMServerConnection me = this;

		Consumer<WampTransport> onOpenServerCallback = this.onOpenCallback;

		return new WampConnection() {

			private Consumer<WampTransport> onOpenCallback;
			public Consumer<WampMessage> onReadClientCallback;
			public Consumer<WampMessage> onReadServerCallback;

			@Override
			public void open(Iterator<SubProtocol> supportedSubProtocols) {

				onOpenServerCallback.accept(new WampTransport() {

					@Override
					public void write(WampMessage wampMessage) {
						Object raw = protocol.encode(wampMessage);
						
						log.debug("server write message: "+wampMessage + " (" +raw+")");
						pool.submit(() -> {
							try {
								WampMessage msg = protocol.decode(raw);
								onReadClientCallback.accept(msg);
							} catch (Exception e) {
								exceptionHandler.accept(e);
							}
						});
					}

					@Override
					public void onRead(Consumer<WampMessage> fn) {
						onReadServerCallback = fn;
					}

					@Override
					public void close() {

					}
				});

				onOpenCallback.accept(new WampTransport() {
					@Override
					public void write(WampMessage wampMessage) {
						Object raw = protocol.encode(wampMessage);

						pool.submit(() -> {
							try {
								WampMessage msg = protocol.decode(raw);
								log.debug("server read message: "+msg+ " (" +raw+")");
								onReadServerCallback.accept(msg);
							} catch (Exception e) {
								exceptionHandler.accept(e);
							}
						});
					}

					@Override
					public void onRead(Consumer<WampMessage> fn) {
						onReadClientCallback = fn;
					}

					@Override
					public void close() {
					}
				});

			}

			@Override
			public void onOpen(Consumer<WampTransport> onOpenCallback) {
				this.onOpenCallback = onOpenCallback;
			}

			@Override
			public void onException(Consumer<Exception> exceptionHandler) {
				me.exceptionHandler = exceptionHandler;
			}

			@Override
			public void close() {
			}
		};

	}

}
