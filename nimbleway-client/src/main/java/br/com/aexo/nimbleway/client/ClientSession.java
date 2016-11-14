package br.com.aexo.nimbleway.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import org.jdeferred.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.aexo.nimbleway.client.connection.ClientTransport;
import br.com.aexo.nimbleway.client.interaction.Invocation;
import br.com.aexo.nimbleway.client.interaction.MessageType;
import br.com.aexo.nimbleway.client.interaction.Publication;
import br.com.aexo.nimbleway.client.interaction.Registration;
import br.com.aexo.nimbleway.client.interaction.RegistrationCall;
import br.com.aexo.nimbleway.client.interaction.ResultCall;
import br.com.aexo.nimbleway.client.interaction.ResultError;
import br.com.aexo.nimbleway.client.interaction.Subscription;
import br.com.aexo.nimbleway.client.interaction.SubscriptionCall;
import br.com.aexo.nimbleway.client.interaction.WampError;
import br.com.aexo.nimbleway.client.interaction.WampException;
import br.com.aexo.nimbleway.client.messages.AbortMessage;
import br.com.aexo.nimbleway.client.messages.CallMessage;
import br.com.aexo.nimbleway.client.messages.EventMessage;
import br.com.aexo.nimbleway.client.messages.GoodByeMessage;
import br.com.aexo.nimbleway.client.messages.HelloMessage;
import br.com.aexo.nimbleway.client.messages.InvocationMessage;
import br.com.aexo.nimbleway.client.messages.PublishMessage;
import br.com.aexo.nimbleway.client.messages.PublishedMessage;
import br.com.aexo.nimbleway.client.messages.RegisterMessage;
import br.com.aexo.nimbleway.client.messages.RegisteredMessage;
import br.com.aexo.nimbleway.client.messages.ReplyErrorMessage;
import br.com.aexo.nimbleway.client.messages.ResultMessage;
import br.com.aexo.nimbleway.client.messages.SubscribeMessage;
import br.com.aexo.nimbleway.client.messages.SubscribedMessage;
import br.com.aexo.nimbleway.client.messages.UnregisterMessage;
import br.com.aexo.nimbleway.client.messages.UnregisteredMessage;
import br.com.aexo.nimbleway.client.messages.UnsubscribeMessage;
import br.com.aexo.nimbleway.client.messages.UnsubscribedMessage;
import br.com.aexo.nimbleway.client.messages.ClientMessage;
import br.com.aexo.nimbleway.client.messages.WelcomeMessage;
import br.com.aexo.nimbleway.client.utils.SpinLock;

/**
 * represent this session to router
 * 
 * @author carlosr
 *
 */
public class ClientSession {

	private static Logger log = LoggerFactory.getLogger(ClientSession.class);

	private ClientTransport transport;

	private Long id;

	private String realm;

	private boolean isOpen = true;

	private Map<Long, Subscription> subscriptions = new HashMap<>();

	private Map<Long, Registration> registrations = new HashMap<>();

	private Consumer<Exception> exceptionHandler;

	private SpinLock spinLock = new SpinLock();

	private Consumer<ClientSession> onOpenCallback;

	private Map<WaitReplyKey, WaitReply<? extends ClientMessage, ? extends Object>> waitReplies = new HashMap<WaitReplyKey, WaitReply<? extends ClientMessage, ? extends Object>>();

	private void waitReply(MessageType type, Long id, WaitReply<? extends ClientMessage, ? extends Object> wr) {
		spinLock.lock();
		waitReplies.put(new WaitReplyKey(type, id), wr);
		spinLock.unlock();
	}

	@SuppressWarnings("unchecked")
	private <T> T retrieve(MessageType type, Long id) {
		WaitReplyKey key = new WaitReplyKey(type, id);
		spinLock.lock();
		WaitReply<? extends ClientMessage, ? extends Object> o = waitReplies.get(key);
		waitReplies.remove(key);
		spinLock.unlock();
		return (T) o;
	}

	public void open(String realm) {
		this.realm = realm;
		transport.write(new HelloMessage(realm));
	}

	protected void configureHandlersInTransport() {
		transport.onRead((msg) -> {

			if (msg instanceof WelcomeMessage) {
				id = ((WelcomeMessage) msg).getSessionId();
				onOpenCallback.accept(this);
			} else if (msg instanceof AbortMessage) {
				AbortMessage message = (AbortMessage) msg;
				exceptionHandler.accept(new WampException(message.getMessage(), message.getWampError()));
			} else if (msg instanceof PublishedMessage) {
				PublishedMessage message = (PublishedMessage) msg;
				WaitReply<PublishMessage, Publication> wr = retrieve(MessageType.PUBLISH, message.getRequestId());
				PublishMessage requestMessage = wr.getMessage();
				Publication publication = requestMessage.getPublication().publicationId(message.getPublicationId());
				wr.resolve(publication);
			} else if (msg instanceof RegisteredMessage) {
				RegisteredMessage message = (RegisteredMessage) msg;
				WaitReply<RegisterMessage, Registration> wr = retrieve(MessageType.REGISTER, message.getRequestId());
				Registration registration = wr.getMessage().getRegistration().registrationId(message.getRegistrationId());
				registrations.put(registration.getId(), registration);
				wr.resolve(registration);
			} else if (msg instanceof SubscribedMessage) {
				SubscribedMessage message = (SubscribedMessage) msg;
				WaitReply<SubscribeMessage, Subscription> wr = retrieve(MessageType.SUBSCRIBE, message.getRequestId());
				Subscription subscription = wr.getMessage().getSubscription().registrationId(message.getRegistrationId());
				subscriptions.put(subscription.getId(), subscription);
				wr.resolve(subscription);
			} else if (msg instanceof UnregisteredMessage) {
				UnregisteredMessage message = (UnregisteredMessage) msg;
				WaitReply<UnregisterMessage, Registration> wr = retrieve(MessageType.UNREGISTER, message.getRequestId());
				Registration registration = wr.getMessage().getRegistration();
				registrations.remove(registration.getId());
				wr.resolve(registration);
			} else if (msg instanceof UnsubscribedMessage) {
				UnsubscribedMessage message = (UnsubscribedMessage) msg;
				WaitReply<UnsubscribeMessage, Subscription> wr = retrieve(MessageType.UNSUBSCRIBE, message.getRequestId());
				Subscription subscription = wr.getMessage().getSubscription();
				subscriptions.remove(subscription.getId());
				wr.resolve(subscription);
			} else if (msg instanceof ResultMessage) {
				ResultMessage message = (ResultMessage) msg;
				WaitReply<CallMessage, ResultCall> wr = retrieve(MessageType.CALL, message.getCallId());
				wr.resolve(new ResultCall(message));
			} else if (msg instanceof ReplyErrorMessage) {
				ReplyErrorMessage message = (ReplyErrorMessage) msg;
				WaitReply<ClientMessage, Object> wr = retrieve(message.getType(), message.getRequestId());
				wr.reject(new WampError(message));
			} else if (msg instanceof InvocationMessage) {
				InvocationMessage message = (InvocationMessage) msg;
				Registration registration = registrations.get(message.getFunctionId());
				RegistrationCall call = new RegistrationCall(message, transport);
				try {
					registration.getCallback().accept(call);
				} catch (Exception e) {
					log.error("invocation error", new IllegalStateException("Protect your procedure correctly with a try catch block and report the occurrence of the error using ResultError for the call in replyWith", e));
					ResultError err = ResultError.error("br.com.aexo.nimbleway.unknown_error").args("An unknown error has occurred");
					call.replyWith(err);
					exceptionHandler.accept(e);
				}
			} else if (msg instanceof EventMessage) {
				EventMessage message = (EventMessage) msg;
				Subscription subscription = subscriptions.get(message.getSubscriptionId());

				SubscriptionCall call = new SubscriptionCall(message);
				try {
					subscription.getCallback().accept(call);
				} catch (Exception e) {
					log.error("There was an error processing event", e);
					exceptionHandler.accept(e);
				}

			} else if (msg instanceof GoodByeMessage) {
				if (isOpen)
					close();
				transport.close();
			}
		});
	}

	public ClientSession(ClientTransport transport, Consumer<ClientSession> onOpenCallback, Consumer<Exception> exceptionHandler) {
		this.transport = transport;
		this.onOpenCallback = onOpenCallback;
		this.exceptionHandler = exceptionHandler;
		configureHandlersInTransport();
	}

	public void close() {
		if (isOpen) {
			isOpen = false;
			transport.write(new GoodByeMessage());
		}
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
		SubscribeMessage msg = new SubscribeMessage(randomId(), subscription);
		return waitReply(MessageType.SUBSCRIBE, msg.getId(), msg);
	}

	public Promise<Subscription, WampError, Object> unsubscribe(Subscription subscription) {
		UnsubscribeMessage msg = new UnsubscribeMessage(randomId(), subscription);
		return waitReply(MessageType.UNSUBSCRIBE, msg.getId(), msg);
	}

	public Promise<Publication, WampError, Object> publish(Publication pub) {
		PublishMessage msg = new PublishMessage(randomId(), pub);
		WaitReply<PublishMessage, Publication> wr = new WaitReply<>(msg);
		Boolean acknowledge = (Boolean) msg.getPublication().getOptions().get("acknowledge");
		if (acknowledge != null && acknowledge) {
			waitReply(MessageType.PUBLISH, msg.getId(), wr);
		}
		transport.write(msg);
		return wr.getPromise();
	}

	public Promise<Registration, WampError, Object> register(Registration registration) {
		RegisterMessage msg = new RegisterMessage(randomId(), registration);
		return waitReply(MessageType.REGISTER, msg.getId(), msg);
	}

	public Promise<Registration, WampError, Object> unregister(Registration registration) {
		UnregisterMessage msg = new UnregisterMessage(randomId(), registration);
		return waitReply(MessageType.UNREGISTER, msg.getId(), msg);
	}

	public Promise<ResultCall, WampError, Object> call(Invocation invocation) {
		CallMessage msg = new CallMessage(randomId(), invocation);
		return waitReply(MessageType.CALL, msg.getId(), msg);
	}

	public <RESOLVED, MESSAGE extends ClientMessage> Promise<RESOLVED, WampError, Object> waitReply(MessageType type, Long id, MESSAGE msg) {
		WaitReply<MESSAGE, RESOLVED> wr = new WaitReply<MESSAGE, RESOLVED>(msg);
		waitReply(type, id, wr);
		transport.write(msg);
		return wr.getPromise();
	}

	private long randomId() {
		return ThreadLocalRandom.current().nextLong(10000000, 99999999);
	}

}
