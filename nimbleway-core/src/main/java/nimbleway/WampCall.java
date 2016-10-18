package nimbleway;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;

public class WampCall {

	private Long id;

	private Deferred<WampResult, Exception, ?> deferred;

	private Promise<WampResult, Exception, ?> promise;

	public WampCall(Long id, Deferred<WampResult, Exception, ?> deferred2,
			Promise<WampResult, Exception, ?> promise2) {
		super();
		this.id = id;
		this.deferred = deferred2;
		this.promise = promise2;
	}

	public Long getId() {
		return id;
	}

	public Deferred<WampResult, Exception, ?> getDeferred() {
		return deferred;
	}

	public Promise<WampResult, Exception, ?> getPromise() {
		return promise;
	}

}
