import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.client.WampClient;
import br.com.aexo.nimbleway.core.Invocation;
import br.com.aexo.nimbleway.core.Publication;
import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.Result;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.router.WampRouter;

public class Main2 {

	public static void main(String[] args) {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "debug");

		Consumer<Exception> exceptionHandler = (e) -> {
			e.printStackTrace();
			throw new RuntimeException(e);
		};

		InVMServerConnection serverConnection = new InVMServerConnection("wamp.2.json");

		WampRouter router = new WampRouter(serverConnection);
		router.onExceptionHandler(exceptionHandler);
		router.start();

		WampClient client1 = new WampClient(serverConnection.newClientConnection());
		WampClient client2 = new WampClient(serverConnection.newClientConnection());

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
			// new Timer().scheduleAtFixedRate(new TimerTask() {
			// public void run() {
			// session.call(Invocation.function("com.my.fn"));
			// }
			// }, 1000L, 2000L);
		});

		client1.open("realm1");
		client2.open("realm2");
	}
}
