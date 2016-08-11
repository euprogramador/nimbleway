package nimbleway;

import rx.Observable;

public abstract class WampSession {

	protected abstract Observable<WampMessage> onMessage();

	public void registerProcedure(String procedureName){
		
		
	}
	
	
}
