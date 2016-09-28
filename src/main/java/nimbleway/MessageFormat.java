package nimbleway;


public interface MessageFormat {

	/**
	 * decodifica um subprotocol websocket
	 * 
	 * @param data
	 * @return 
	 */
	WampMessage decode(Object data);

}
