package br.com.aexo.nimbleway;

import java.util.function.Consumer;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.messagehandlers.MessageHandlers;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.RegisterMessage;

public class WampSession {

	private WampTransport transport;
	private String reaml;
	
	private MessageHandlers messageHandlers;
	
	
	// uma session usa o transporte para se comunicar e executar o protocolo
	// wamp
	public WampSession(WampTransport transport, String realm) {
		this.messageHandlers = new MessageHandlers(transport,realm);
		this.transport = transport;
		this.reaml = realm;
		// substitui o leitor da mensagem
		transport.onRead((msg) -> {
			messageHandlers.handle(msg);
		});
	}

	public void register(String name,Consumer<WampInvocation> fn) {
		RegisterMessage register = new RegisterMessage(name,fn);
		messageHandlers.handle(register);
		transport.write(register);
	}

	public void publish() {
	}

	public void subscribe() {
	}


	public Promise<WampResult, Exception, ?> call(String fn, Object... params ) {
		CallMessage msg = new CallMessage(fn,params);
		messageHandlers.handle(msg);
		transport.write(msg);
		return msg.getPromise();
	}



}
