import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import br.com.aexo.nimbleway.client.WampClient;
import br.com.aexo.nimbleway.core.Subscription;
import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;
import br.com.aexo.nimbleway.router.WampRouter;

public class Main2 {

	public static void main(String[] args) {

		PipeConnection vm = new PipeConnection();
		
		new WampRouter(vm.point1()).start();
		WampClient client = new WampClient(vm.point2());

		client.onOpen((session) -> {
			System.out.println("abriu");

			Subscription sub = Subscription.toTopic("1").callback((callTopic) -> {
			});

			session.subscribe(sub).then((_sub) -> {
				System.out.println("OK");
			}).fail((e) -> {
				System.out.println("falhou");
			});

		});

		client.open("realm1");
	}
}

class PipeConnection {

	private InVMWampConnection conn1;
	private InVMWampConnection conn2;

	PipeConnection() {

		ExecutorService pool = Executors.newCachedThreadPool();

		PipeWampTransport t1 = new PipeWampTransport(pool);
		PipeWampTransport t2 = new PipeWampTransport(pool);

		t1.connect(t2);
		t2.connect(t1);

		conn1 = new InVMWampConnection(t1);
		conn2 = new InVMWampConnection(t2);
	}

	public WampConnection point1() {
		return conn1;
	}

	public WampConnection point2() {
		return conn2;
	}

}

class PipeWampTransport implements WampTransport {

	private Consumer<WampMessage> onRead;

	private PipeWampTransport transport;

	private ExecutorService pool;

	public PipeWampTransport(ExecutorService pool) {
		this.pool = pool;
	}

	@Override
	public void onRead(Consumer<WampMessage> fn) {
		this.onRead = fn;

	}

	public void connect(PipeWampTransport transport) {
		this.transport = transport;
	}

	@Override
	public void write(WampMessage wampMessage) {
		pool.submit(() -> {
			try {
				transport.onRead.accept(wampMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void close() {
	}

}

class InVMWampConnection implements WampConnection {

	private Consumer<WampTransport> onOpenCallback;
	private PipeWampTransport transport;

	public InVMWampConnection(PipeWampTransport transport) {
		this.transport = transport;
	}

	@Override
	public void open(Iterator<SubProtocol> supportedSubProtocols) {
		onOpenCallback.accept(transport);
	}

	@Override
	public void close() {
	}

	@Override
	public void onOpen(Consumer<WampTransport> onOpenCallback) {
		this.onOpenCallback = onOpenCallback;
	}

	@Override
	public void onException(Consumer<Exception> exceptionHandler) {
	}

}
