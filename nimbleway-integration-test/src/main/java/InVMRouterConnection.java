import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.aexo.nimbleway.client.connection.ClientConnection;
import br.com.aexo.nimbleway.client.connection.ClientTransport;
import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.subprotocols.ClientSubProtocol;
import br.com.aexo.nimbleway.router.connection.RouterConnection;
import br.com.aexo.nimbleway.router.connection.RouterMessage;
import br.com.aexo.nimbleway.router.connection.RouterTransport;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterSubProtocol;

public class InVMRouterConnection implements RouterConnection {

	private static Logger log = LoggerFactory.getLogger(InVMRouterConnection.class) ;
	
	private Consumer<RouterTransport> onOpenCallback;
	private Consumer<Exception> exceptionHandler;

	private ExecutorService pool = Executors.newCachedThreadPool();

	private RouterSubProtocol serverSubProtocol;

	private ClientSubProtocol clientSubProtocol;

	public InVMRouterConnection(RouterSubProtocol serverSubProtocol,ClientSubProtocol clientSubProtocol) {
		this.serverSubProtocol = serverSubProtocol;
		this.clientSubProtocol = clientSubProtocol;
	}

	@Override
	public void open(Iterator<RouterSubProtocol> supportedSubProtocols) {
	}

	@Override
	public void close() {
	}

	@Override
	public void onOpen(Consumer<RouterTransport> onOpenCallback) {
		this.onOpenCallback = onOpenCallback;
	}

	@Override
	public void onException(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public ClientConnection newClientConnection() {

		InVMRouterConnection me = this;

		Consumer<RouterTransport> onOpenRouterCallback = this.onOpenCallback;

		return new ClientConnection() {

			private Consumer<ClientTransport> onOpenCallback;
			public Consumer<ClientMessage> onReadClientCallback;
			public Consumer<RouterMessage> onReadRouterCallback;

			@Override
			public void open(Iterator<br.com.aexo.nimbleway.client.subprotocols.ClientSubProtocol> supportedSubProtocols) {

				onOpenRouterCallback.accept(new RouterTransport() {

					@Override
					public void write(RouterMessage wampMessage) {
						Object raw = serverSubProtocol.encode(wampMessage);
						
						log.debug("server write message: "+wampMessage + " (" +raw+")");
						pool.submit(() -> {
							try {
								ClientMessage msg = clientSubProtocol.decode(raw);
								onReadClientCallback.accept(msg);
							} catch (Exception e) {
								exceptionHandler.accept(e);
							}
						});
					}

					@Override
					public void onRead(Consumer<RouterMessage> fn) {
						onReadRouterCallback = fn;
					}

					@Override
					public void close() {

					}
				});

				onOpenCallback.accept(new ClientTransport() {
					@Override
					public void write(ClientMessage wampMessage) {
						Object raw = clientSubProtocol.encode(wampMessage);

						pool.submit(() -> {
							try {
								RouterMessage msg = serverSubProtocol.decode(raw);
								log.debug("server read message: "+msg+ " (" +raw+")");
								onReadRouterCallback.accept(msg);
							} catch (Exception e) {
								exceptionHandler.accept(e);
							}
						});
					}

					@Override
					public void onRead(Consumer<ClientMessage> fn) {
						onReadClientCallback = fn;
					}

					@Override
					public void close() {
					}
				});

			}

			@Override
			public void onOpen(Consumer<ClientTransport> onOpenCallback) {
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
