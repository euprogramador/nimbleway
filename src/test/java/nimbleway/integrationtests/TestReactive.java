package nimbleway.integrationtests;

import java.util.HashMap;
import java.util.Map;

import nimbleway.ServerConnection;
import nimbleway.SubProtocol;
import nimbleway.UndertowWebSocketServerConnection;
import nimbleway.Wamp2JsonSubProtocol;
import nimbleway.WampRouter;

public class TestReactive {

	public static void main(String[] args) throws Exception {

		Map<String, SubProtocol> supportedSubProtocols = new HashMap<String, SubProtocol>();
		supportedSubProtocols.put("wamp.2.json", new Wamp2JsonSubProtocol());

		ServerConnection conn = new UndertowWebSocketServerConnection("ws://localhost:8080/services", supportedSubProtocols);

		WampRouter router = new WampRouter(conn);

		router.onListen().subscribe((session) -> {
			System.out.println("session-Thread: " + Thread.currentThread().getName());
			System.out.println("session: " + session);
		});

		router.listen();
	}
}
