package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.WampResult;

/**
 * 
 * represent wamp call message
 * 
 * @author carlosr
 *
 */
public class CallMessage implements WampMessage {

	private Object[] params;
	private String fnName;
	private Long id;
	private Deferred<WampResult, Exception, Object> def;
	private Promise<WampResult, Exception, Object> promise;

	public CallMessage(String fnName, Object[] params) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.fnName = fnName;
		this.params = params;
		this.def = new DeferredObject<WampResult, Exception, Object>();
		this.promise = def.promise();
	}

	public Object[] getParams() {
		return params;
	}

	public String getFnName() {
		return fnName;
	}

	public Long getId() {
		return id;
	}

	public Deferred<WampResult, Exception, Object> getDefered() {
		return def;
	}

	public Promise<WampResult, Exception, Object> getPromise() {
		return promise;
	}

}
