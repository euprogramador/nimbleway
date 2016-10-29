package br.com.aexo.nimbleway;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.AbortMessage;
import br.com.aexo.nimbleway.messages.HelloMessage;
import br.com.aexo.nimbleway.messages.WelcomeMessage;

/**
 * component used for handshake process handle to router
 * 
 * @author carlosr
 *
 */
@Component
public class WampClientHandshake {
	
	private static Logger log = LoggerFactory.getLogger(WampClientHandshake.class);

	private WampTransport transport;
	private Consumer<WampSession> onHandshakeCallback;
	private WampSession session;

	private Consumer<Exception> exceptionHandler;

	public WampClientHandshake(WampTransport transport, WampSession session) {
		this.transport = transport;
		this.session = session;
	}

	/**
	 * start handshake using transport for comunication to router
	 * 
	 * @param realm
	 */
	public void handshake(String realm) {
		
		log.debug("handshake to realm "+ realm);

		this.transport.onRead((message) -> {
			
			// TODO adicionar suporte para outras mensagens de erro no handshake
				if (message instanceof WelcomeMessage) {
					log.debug("handshake success");
					// success of handshake
					session.configureHandlersInTransport();
					onHandshakeCallback.accept(session);
				}
				
				if (message instanceof AbortMessage) {
					AbortMessage abort = (AbortMessage) message;
					exceptionHandler.accept(new WampException(abort.getMessage(),abort.getWampError()));
				}
			});

		// initHandshakeProccess
		transport.write(new HelloMessage(realm));
	}

	/**
	 * callback called for handshake process concluded
	 * 
	 * @param onHandshakeCallback
	 */
	public void onHandshake(Consumer<WampSession> onHandshakeCallback) {
		log.trace("configured on handshake handler");
		this.onHandshakeCallback = onHandshakeCallback;
	}

	public void onException(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
}