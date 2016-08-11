package nimbleway;


/**
 * classe que decodifica o subprotocolo usado no wamp
 * 
 * @author carlosr
 *
 */
public class Wamp2JsonSubProtocol implements SubProtocol {

	@Override
	public WampMessage decode(Object data) {
		return new DummyWampMessage(data);
	}

}
