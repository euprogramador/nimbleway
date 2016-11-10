package br.com.aexo.nimbleway.client;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.core.SessionPreparator;
import br.com.aexo.nimbleway.core.WampException;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.AbortMessage;
import br.com.aexo.nimbleway.core.messages.HelloMessage;
import br.com.aexo.nimbleway.core.messages.WelcomeMessage;

/**
 * component used for handshake process handle to router
 * 
 * @author carlosr
 *
 */
@Component
class WampClientHandshake {
	
	private static Logger log = LoggerFactory.getLogger(WampClientHandshake.class);

	private WampTransport transport;
	private Consumer<ClientSession> onHandshakeCallback;
	private ClientSession session;

	private Consumer<Exception> exceptionHandler;

	private SessionPreparator preparator;

	public WampClientHandshake(WampTransport transport, ClientSession session,SessionPreparator preparator) {
		this.transport = transport;
		this.session = session;
		this.preparator = preparator;
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
					WelcomeMessage welcome = (WelcomeMessage) message;
					log.debug("handshake success");
					// success of handshake
					
					preparator.prepareSession(welcome.getSessionId(), realm,exceptionHandler);
					
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
	public void onHandshake(Consumer<ClientSession> onHandshakeCallback) {
		log.trace("configured on handshake handler");
		this.onHandshakeCallback = onHandshakeCallback;
	}

	public void onException(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
}
