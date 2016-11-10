package br.com.aexo.nimbleway.client;

import java.util.List;

import org.jdeferred.Promise;

import br.com.aexo.nimbleway.core.Invocation;
import br.com.aexo.nimbleway.core.Publication;
import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.ResultCall;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.WampError;

public interface ClientSession {

	public Long getId();

	public String getRealm();

	public Promise<Subscription, WampError, Object> subscribe(Subscription subscription);

	public Promise<Subscription, WampError, Object> unsubscribe(Subscription subscription);

	public List<Subscription> getSubscriptions();

	public Promise<Publication, WampError, Object> publish(Publication pub);

	public Promise<Registration, WampError, Object> register(Registration registration);

	public Promise<Registration, WampError, Object> unregister(Registration registration);

	public List<Registration> getRegistrations();

	public Promise<ResultCall, WampError, Object> call(Invocation invocation);

}