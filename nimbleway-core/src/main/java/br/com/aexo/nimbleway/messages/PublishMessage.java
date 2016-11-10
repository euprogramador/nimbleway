package br.com.aexo.nimbleway.messages;

import java.util.concurrent.ThreadLocalRandom;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import br.com.aexo.nimbleway.Publication;
import br.com.aexo.nimbleway.WampError;

public class PublishMessage extends DeferredWampMessage<Publication, WampError> {

	private Long id;
	private Deferred<Publication, WampError, Object> def;
	private Promise<Publication, WampError, Object> promise;
	private Publication publication;


	public PublishMessage(Publication publication) {
		this.id = ThreadLocalRandom.current().nextLong(10000000, 99999999);
		this.def = new DeferredObject<Publication, WampError, Object>();
		this.promise = def.promise();
		this.publication = publication;
	}

	public Long getId() {
		return id;
	}



	public Publication getPublication() {
		return publication;
	}

	public Promise<Publication, WampError, Object> getPromise() {
		return promise;
	}
	
	protected Deferred<Publication, WampError, Object> getDefered() {
		return def;
	}

}
