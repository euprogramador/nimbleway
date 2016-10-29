package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.WampResult;

public class PublishMessage implements WampMessage {

	private Object[] params;
	private Long id;
	private Deferred<WampResult, Exception, Object> def;
	private Promise<WampResult, Exception, Object> promise;
	private String topic;

	public PublishMessage(String topic, Object[] params) {
		this.topic = topic;
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.params = params;
		this.def = new DeferredObject<WampResult, Exception, Object>();
		this.promise = def.promise();
	}

	public Object[] getParams() {
		return params;
	}

	public String getTopic() {
		return topic;
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
