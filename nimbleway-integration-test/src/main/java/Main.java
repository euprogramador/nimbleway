import io.undertow.server.DefaultByteBufferPool;

import java.net.URI;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Xnio;
import org.xnio.XnioWorker;

import br.com.aexo.nimbleway.client.WampClient;
import br.com.aexo.nimbleway.connection.UndertowWebSocketClientConnection;
import br.com.aexo.nimbleway.core.Invocation;
import br.com.aexo.nimbleway.core.Publication;
import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.Result;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;

public class Main {

	public static void main(String[] args) throws Exception {
		
		
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "trace");

		Xnio xnio = Xnio.getInstance("nio", Main.class.getClassLoader());

		XnioWorker worker = xnio.createWorker(OptionMap.builder() //
				.set(Options.WORKER_IO_THREADS, 8) //
				.set(Options.CONNECTION_HIGH_WATER, 1000000) //
				.set(Options.CONNECTION_LOW_WATER, 1000000) //
				.set(Options.WORKER_TASK_CORE_THREADS, 30) //
				.set(Options.WORKER_TASK_MAX_THREADS, 30) //
				.set(Options.TCP_NODELAY, true) //
				.set(Options.CORK, true) //
				.getMap());

		DefaultByteBufferPool bufferPool = new DefaultByteBufferPool(true, 8192 * 3, 1000, 10, 100);

		ExecutorService executorService = Executors.newFixedThreadPool(30);

		WampConnection conn = new UndertowWebSocketClientConnection(worker, bufferPool, new URI("ws://localhost:8080/ws"), executorService);

		WampClient client = new WampClient(conn);

		client.onException((e) -> {
			e.printStackTrace();
			throw new RuntimeException(e);
		});

		client.onOpen((session) -> {

			
			System.out.println(session.getId());

			System.out.println(session.getRealm());
			Subscription topic = Subscription.toTopic("com.myapp.service").option("match", "prefix").callback((chamada) -> {
				System.out.println("topico invocado");
				System.out.println(chamada.params());
				System.out.println(chamada.params(0).as(Integer.class));
				System.out.println(chamada.params(1).as(Integer.class));
				System.out.println(chamada.params(2).as(Integer.class));

				System.out.println(chamada.payload("teste").as(String.class));

				System.out.println(chamada.details());

				Subscription subscription = session.getSubscriptions().get(0);

				session.unsubscribe(subscription).then((sub) -> {

					System.out.println("unsubscribed");
					System.out.println(session.getSubscriptions());
				}).fail((e) -> {

					System.out.println("falhou a unsubscribe");
				});
			});

			session.subscribe(topic).then((subscription) -> {
				System.out.println("sucesso");
				System.out.println(session.getSubscriptions());

				Publication pub = Publication.toTopic("com.myapp.service").option("acknowledge", true).option("exclude_me", false).args(1, 2, 3).payload("teste", "123");

				session.publish(pub).then((publication) -> {
					System.out.println("Publicou com sucesso na fila");

				}).fail((e) -> {
					System.out.println("Falhou ao publicar na fila");
				});

			}).fail((error) -> {
				System.out.println("falhou");
			});

			Registration reg = Registration.toName("com.myapp.fn").option("invoke", "roundrobin").callback((chamada) -> {
				System.out.println("função invocada");

				Integer a = chamada.params(0).as(Integer.class);
				Integer b = chamada.params(1).as(Integer.class);

				Integer c = a + b;

				Result result = Result.create().args(c).payload("resultado", "enviado");

				chamada.replyWith(result);

			});

			System.out.println("Registros:" + session.getRegistrations());

			session.register(reg).then((registration) -> {
				System.out.println("registro com sucesso");
				System.out.println("Registros:" + session.getRegistrations());

				Invocation invocation = Invocation.function("com.myapp.fn").args(1, 2).payload("teste", 123);

				session.call(invocation).then((result) -> {
					System.out.println("invocado com sucesso");
					System.out.println("resultado:" + result.params(0).as(Integer.class));
					System.out.println("resultado payload:" + result.payload("resultado").as(String.class));

					session.unregister(registration).then((regis) -> {
						System.out.println("desregistrado com sucesso");
						System.out.println("Registros:" + session.getRegistrations());
					}).fail((e) -> {
						System.out.println("ocorreu um erro ao desregistrar");
					});

					session.unregister(registration).then((regis) -> {
						System.out.println("desregistrado com sucesso");
						System.out.println("Registros:" + session.getRegistrations());
					}).fail((e) -> {
						System.out.println("ocorreu um erro ao desregistrar: " + e.getError());
					});

				}).fail((e) -> {
					System.out.println("ocorreu um erro ao efetuar a invocação");

				});

			}).fail((e) -> {
				System.out.println("falhou ao registrar");
			});

			Registration regFailCall = Registration.toName("com.myapp.sfn").callback((chamada) -> {
				System.out.println("chamou....");

				throw new RuntimeException("ocorreu um erro na chamada");
			});

			session.register(regFailCall).then((r) -> {
				System.out.println("registro da falha com sucesso");

				Invocation i = Invocation.function("com.myapp.sfn");
				session.call(i).then((result) -> {
					System.out.println("tudo certo (FAIL)");
				}).fail((error) -> {
					System.out.println("ocorreu um erro (Esperado):" + error.getError() + " f:f " + error.params(0).as(String.class));
				});

			}).fail((e) -> {
				System.out.println("ocorreu um erro ao registrar a falha  " + e.getError());
			});

		});

		client.open("realm1");

	}
}
