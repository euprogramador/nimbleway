package nimbleway;

import rx.Observable;


public interface ServerConnection {

	void listen();

	Observable<WampSession> onListen();

}
