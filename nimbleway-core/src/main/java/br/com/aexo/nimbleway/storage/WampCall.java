package br.com.aexo.nimbleway.storage;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;

import br.com.aexo.nimbleway.WampResult;


/**
 * represent one call with deferred and promise objects for result
 * 
 * @author carlosr
 *
 */
public class WampCall {

	private Long id;

	private Deferred<WampResult, Exception, Object> deferred;

	private Promise<WampResult, Exception, Object> promise;

	public WampCall(Long id, Deferred<WampResult, Exception, Object> deferred, Promise<WampResult, Exception, Object> promise) {
		super();
		this.id = id;
		this.deferred = deferred;
		this.promise = promise;
	}

	public Long getId() {
		return id;
	}

	public Deferred<WampResult, Exception, Object> getDeferred() {
		return deferred;
	}

	public Promise<WampResult, Exception, Object> getPromise() {
		return promise;
	}

}
