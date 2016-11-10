package br.com.aexo.nimbleway.client.core;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;


@Component
public class SessionPreparator {
	
	private DefaultClientSession session;

	public SessionPreparator(ClientSession session) {
		this.session = (DefaultClientSession) session;
	}
	
	public void prepareSession(Long sessionId,String realm, Consumer<Exception> exceptionHandler){
		session.setId(sessionId);
		session.setRealm(realm);
		session.setExceptionHandler(exceptionHandler);
		session.configureHandlersInTransport();
	}

}
