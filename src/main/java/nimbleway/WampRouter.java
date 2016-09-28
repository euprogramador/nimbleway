package nimbleway;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class WampRouter {

	private ServerConnection serverConnection;
	private Observable<WampSession> onListen;
	private Subscriber<? super WampSession> onListenSubscriber;
	private WampContext wampContext;

	public WampRouter(ServerConnection serverConnection) {
		this.wampContext = new WampContext();
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
			session.onMessage().subscribe((msg)->{
//				session.
			});
			
			onListenSubscriber.onNext(session);
		});

		serverConnection.listen();
	}
	
	
	
	
	
}
