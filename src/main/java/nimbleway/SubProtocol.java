package nimbleway;


public interface SubProtocol {

	/**
	 * decodifica um subprotocol websocket
	 * 
	 * @param data
	 * @return 
	 */
	WampMessage decode(Object data);

}
