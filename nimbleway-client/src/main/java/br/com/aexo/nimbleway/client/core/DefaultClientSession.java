package br.com.aexo.nimbleway.client.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jdeferred.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.client.ClientSession;
import br.com.aexo.nimbleway.core.Invocation;
import br.com.aexo.nimbleway.core.Publication;
import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.RegistrationCall;
import br.com.aexo.nimbleway.core.ResultCall;
import br.com.aexo.nimbleway.core.ResultError;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.SubscriptionCall;
import br.com.aexo.nimbleway.core.WampError;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.CallMessage;
import br.com.aexo.nimbleway.core.messages.EventMessage;
import br.com.aexo.nimbleway.core.messages.GoodByeMessage;
import br.com.aexo.nimbleway.core.messages.InvocationMessage;
import br.com.aexo.nimbleway.core.messages.PublishMessage;
import br.com.aexo.nimbleway.core.messages.RegisterMessage;
import br.com.aexo.nimbleway.core.messages.SubscribeMessage;
import br.com.aexo.nimbleway.core.messages.UnregisterMessage;
import br.com.aexo.nimbleway.core.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.core.messages.WampMessage;

/**
 * represent this session to router
 * 
 * @author carlosr
 *
 */
@Component
class DefaultClientSession implements ClientSession {

	private static Logger log = LoggerFactory.getLogger(DefaultClientSession.class);
	
	@SuppressWarnings("rawtypes")
	private List<MessageHandler> messageHandlers;

	private WampTransport transport;

	@SuppressWarnings("unchecked")
	private Consumer<WampMessage> messageHandler = (msg) -> {

		messageHandlers.stream() //
				.filter((handler) -> {
					return handler.isHandleOf(msg);
				}) //
				.forEach((handler) -> handler.handle(msg, this));
	};

	private Long id;

	private String realm;

	private Map<Long, Subscription> subscriptions = new HashMap<>();

	private Map<Long, Registration> registrations = new HashMap<>();

	private Consumer<Exception> exceptionHandler;

	protected void configureHandlersInTransport() {
		transport.onRead((msg) -> {
			messageHandler.accept(msg);
		});
	}

	public DefaultClientSession(@SuppressWarnings("rawtypes") List<MessageHandler> messageHandlers, WampTransport transport) {
		this.messageHandlers = messageHandlers;
		this.transport = transport;
	}


	public void close() { // TODO refactor to new
							// GoodByeMessage().waitReplyFromRouter()
		messageHandler.accept(new GoodByeMessage(true));
	}

	public List<Subscription> getSubscriptions() {
		return Collections.unmodifiableList(new ArrayList<Subscription>(subscriptions.values()));
	}
	
	public List<Registration> getRegistrations() {
		return Collections.unmodifiableList(new ArrayList<Registration>(registrations.values()));
	}

	public Long getId() {
		return id;
	}

	public String getRealm() {
		return realm;
	}

	public Promise<Subscription, WampError, Object> subscribe(Subscription subscription) {
		SubscribeMessage msg = new SubscribeMessage(subscription);
		messageHandler.accept(msg);
		return msg.getPromise();
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected void setRealm(String realm) {
		this.realm = realm;
	}

	public void save(Subscription subscription) {
		subscriptions.put(subscription.getId(), subscription);
	}

	public Promise<Subscription, WampError, Object> unsubscribe(Subscription subscription) {
		UnsubscribeMessage msg = new UnsubscribeMessage(subscription);
		messageHandler.accept(msg);
		return msg.getPromise();
	}

	public void remove(Subscription subscription) {
		subscriptions.remove(subscription.getId());
	}

	public Promise<Publication, WampError, Object> publish(Publication pub) {
		PublishMessage msg = new PublishMessage(pub);
		messageHandler.accept(msg);
		return msg.getPromise();
	}

	public void dispachEvent(EventMessage message) {
		Subscription subscription = subscriptions.get(message.getSubscriptionId());

		SubscriptionCall call = new SubscriptionCall(message);
		try {
			subscription.getCallback().accept(call);
		} catch(Exception e){
			log.error("There was an error processing event", e);
			exceptionHandler.accept(e);
		}
	}

	public Promise<Registration, WampError, Object> register(Registration registration) {
		RegisterMessage message = new RegisterMessage(registration);
		messageHandler.accept(message);
		return message.getPromise();
	}

	public void save(Registration registration) {
		registrations.put(registration.getId(), registration);
	}

	public Promise<Registration, WampError, Object> unregister(Registration registration) {
		UnregisterMessage message = new UnregisterMessage(registration);
		messageHandler.accept(message);
		return message.getPromise();
	}

	public void remove(Registration registration) {
		registrations.remove(registration.getId());
	}

	public Promise<ResultCall, WampError, Object> call(Invocation invocation) {
		CallMessage msg = new CallMessage(invocation);
		messageHandler.accept(msg);
		return msg.getPromise();
	}

	public void invoke(InvocationMessage message) {
		Registration registration = registrations.get(message.getFunctionId());
		RegistrationCall call = new RegistrationCall(message,transport);
		try {
			registration.getCallback().accept(call);
		} catch(Exception e){
			log.error("invocation error",new IllegalStateException("Protect your procedure correctly with a try catch block and report the occurrence of the error using ResultError for the call in replyWith",e));
			ResultError err = ResultError.error("br.com.aexo.nimbleway.unknown_error").args("An unknown error has occurred");
			call.replyWith(err);
			exceptionHandler.accept(e);
		}
	}

	public void setExceptionHandler(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
