package nimbleway;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class WampRouter {

	private ServerConnection serverConnection;
	private Observable<WampSession> onListen;
	private Subscriber<? super WampSession> onListenSubscriber;

	public WampRouter(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;

		onListen = Observable.create((subscriber) -> {
			onListenSubscriber = subscriber;
		});
		onListen = onListen.observeOn(Schedulers.computation());
		onListen.subscribe();
	}

	public Observable<WampSession> onListen() {
		return onListen;
	}

	public void listen(){
		serverConnection.onListen().subscribe((session)->{
			// delega a sessao recebida
			
			onListenSubscriber.onNext(session);
			
			session.onMessage().subscribe((msg)->{
				System.out.println(Thread.currentThread().getName());
				System.out.println(msg);
				
			});
			
		});

		serverConnection.listen();
	}
}
