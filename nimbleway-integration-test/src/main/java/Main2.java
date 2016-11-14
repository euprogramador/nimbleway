import java.util.ServiceLoader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.client.WampClient;
import br.com.aexo.nimbleway.client.interaction.Invocation;
import br.com.aexo.nimbleway.client.interaction.Publication;
import br.com.aexo.nimbleway.client.interaction.Registration;
import br.com.aexo.nimbleway.client.interaction.Result;
import br.com.aexo.nimbleway.client.subprotocols.ClientSubProtocol;
import br.com.aexo.nimbleway.router.WampRouter;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterSubProtocol;

public class Main2 {

	public static void main(String[] args) throws Exception {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "debug");

		Consumer<Exception> exceptionHandler = (e) -> {
			e.printStackTrace();
			throw new RuntimeException(e);
		};

		ServiceLoader<RouterSubProtocol> serverSubProtocols = ServiceLoader.load(RouterSubProtocol.class);
		RouterSubProtocol serverSubProtocol = serverSubProtocols.iterator().next();

		ServiceLoader<ClientSubProtocol> clientSubProtocols = ServiceLoader.load(ClientSubProtocol.class);
		ClientSubProtocol clientSubProtocol = clientSubProtocols.iterator().next();
		
		
		InVMRouterConnection routerConnection = new InVMRouterConnection(serverSubProtocol,clientSubProtocol);

		WampRouter router = new WampRouter(routerConnection);
		router.onExceptionHandler(exceptionHandler);
		router.start();

		WampClient client1 = new WampClient(routerConnection.newClientConnection());
		WampClient client2 = new WampClient(routerConnection.newClientConnection());

		client1.onException(exceptionHandler);
		client2.onException(exceptionHandler);

		client1.onOpen((session) -> {
			System.out.println("sessão cliente 1: " + session.getId());

			session.register(Registration.toName("com.my.fn").callback((chamada) -> {
				System.out.println("invocou a função");
				chamada.replyWith(Result.create());
				session.publish(Publication.toTopic("com.my.topic"));
			})).then((Registration reg) -> {
				System.out.println("registro da função efetuado com sucesso");
			}).fail((e) -> {
				System.out.println("Ocorreu um erro ao efetuar o registro da função: " + e.getError());
			});
		});

		client2.onOpen((session) -> {
			System.out.println("sessão cliente 2: " + session.getId());

			// session.subscribe(Subscription.toTopic("com.my.topic").callback((chamada)
			// -> {
			// System.out.println("chamou o topico");
			// }));
			//
			new Timer().scheduleAtFixedRate(new TimerTask() {
				public void run() {
					session.call(Invocation.function("com.my.fn"));
				}
			}, 1000L, 3000L);
		});

		client1.open("realm1");
		Thread.currentThread().sleep(100);
		client2.open("realm2");
	}
}
