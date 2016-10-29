package br.com.aexo.nimbleway;

import java.util.List;
import java.util.function.Consumer;

import org.jdeferred.Promise;
import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.handlers.MessageHandler;
import br.com.aexo.nimbleway.messages.CallMessage;
import br.com.aexo.nimbleway.messages.GoodByeMessage;
import br.com.aexo.nimbleway.messages.RegisterMessage;
import br.com.aexo.nimbleway.messages.SubscribeMessage;
import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.messages.PublishMessage;

/**
 * represent this session to router
 * 
 * @author carlosr
 *
 */
@Component
public class WampSession {

	@SuppressWarnings("rawtypes")
	private List<MessageHandler> messageHandlers;

	private WampTransport transport;

	@SuppressWarnings("unchecked")
	private Consumer<WampMessage> messageHandler = (msg) -> {

		messageHandlers.stream() //
				.filter((handler) -> {
					return handler.isHandleOf(msg);
				}) //
				.forEach((handler) -> handler.handle(msg));
	};

	protected void configureHandlersInTransport() {
		transport.onRead((msg) -> {
			messageHandler.accept(msg);
		});
	}

	public WampSession(@SuppressWarnings("rawtypes") List<MessageHandler> messageHandlers, WampTransport transport) {
		this.messageHandlers = messageHandlers;
		this.transport = transport;
	}

	/**
	 * register function in router
	 * 
	 * @param name
	 * @param fn
	 */
	public void register(String name, Consumer<WampInvocationResult> fn) {
		messageHandler.accept(new RegisterMessage(name, fn));
	}
	
	// TODO implementar passagem de parametros para a chamada conforme a especificação prevê
	public Promise<WampResult, Exception, Object> publish(String topic, Object...params) {
		PublishMessage msg = new PublishMessage(topic, params);
		messageHandler.accept(msg);
		return msg.getPromise();
		
		
	}

	public void subscribe(String topic,Consumer<WampInvocation> fn) {
		messageHandler.accept(new SubscribeMessage(topic, fn));
	}

	/**
	 * invoke function
	 * 
	 * @param fn
	 * @param params
	 * @return
	 */
	// TODO implementar passagem de parametros para a chamada conforme a especificação prevê
	public Promise<WampResult, Exception, ?> call(String fn, Object... params) {
		CallMessage msg = new CallMessage(fn, params);
		messageHandler.accept(msg);
		return msg.getPromise();
	}

	/**
	 * close session
	 */
	public void close() { // TODO refactor to new GoodByeMessage().waitReplyFromRouter()
		messageHandler.accept(new GoodByeMessage(true));
	}

}
