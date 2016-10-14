package br.com.aexo.nimbleway;

public class ITest {
	
	public static void main(String[] args) {
		
		WampConnection conn = null; // new UndertowWebsocketConnection("ws://host/");
		
		WampClient client = new WampClient(conn);
		
		client.onOpen((session)->{
			
			// session.register("fn",()->{});
			// session.publish("")
			// session.call("teste",new Object[]{"1"});
			
		});
		
		client.open("realm1");
		
	}

}
