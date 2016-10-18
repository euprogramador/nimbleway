package nimbleway;

import java.util.List;
import java.util.function.Consumer;

import nimbleway.handlers.MessageHandler;
import nimbleway.messages.CallMessage;
import nimbleway.messages.RegisterMessage;
import nimbleway.messages.WampMessage;

import org.jdeferred.Promise;
import org.springframework.stereotype.Component;

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

	public WampSession(@SuppressWarnings("rawtypes") List<MessageHandler> messageHandlers,
			WampTransport transport) {
		this.messageHandlers = messageHandlers;
		this.transport = transport;
	}

	public void register(String name, Consumer<WampInvocation> fn) {
		RegisterMessage register = new RegisterMessage(name, fn);
		messageHandler.accept(register); 
		transport.write(register);
	}

	public void publish() {
	}

	public void subscribe() {
	}

	public Promise<WampResult, Exception, ?> call(String fn, Object... params) {
		CallMessage msg = new CallMessage(fn, params);
		messageHandler.accept(msg);
		transport.write(msg);
		return msg.getPromise();
	}


}
