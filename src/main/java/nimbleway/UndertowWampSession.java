package nimbleway;

import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedBinaryMessage;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class UndertowWampSession extends WampSession {


	private Observable<WampMessage> onMessageObserver;
	private Subscriber<? super WampMessage> onMessageSubscriber;

	public UndertowWampSession(WebSocketChannel channel, MessageFormat messageFormat) {

		onMessageObserver = Observable.create((subscriber) -> {
			onMessageSubscriber = subscriber;
		});

		onMessageObserver = onMessageObserver.observeOn(Schedulers.computation());
		onMessageObserver.subscribe();

		channel.getReceiveSetter().set(new AbstractReceiveListener() {

			public void decode(Object message) {
				// decode message and send to observer
				WampMessage decodedMessage = messageFormat.decode(message);
				onMessageSubscriber.onNext(decodedMessage);
			}

			@Override
			protected void onFullBinaryMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
				decode(message.getData());
			}

			@Override
			protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
				decode(message.getData());
			}
		});
		
		channel.resumeReceives();
	}

	protected Observable<WampMessage> onMessage() {
		return onMessageObserver;
	}

}
