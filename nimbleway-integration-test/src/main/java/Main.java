import io.undertow.server.DefaultByteBufferPool;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import nimbleway.UndertowWebSocketClientConnection;
import nimbleway.WampClient;
import nimbleway.WampConnection;

import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Xnio;
import org.xnio.XnioWorker;



public class Main {

	public static void main(String[] args) throws Exception {

		Xnio xnio = Xnio.getInstance("nio", Main.class.getClassLoader());

		XnioWorker worker = xnio.createWorker(OptionMap.builder()
				.set(Options.WORKER_IO_THREADS, 8)
				.set(Options.CONNECTION_HIGH_WATER, 1000000)
				.set(Options.CONNECTION_LOW_WATER, 1000000)
				.set(Options.WORKER_TASK_CORE_THREADS, 30)
				.set(Options.WORKER_TASK_MAX_THREADS, 30)
				.set(Options.TCP_NODELAY, true).set(Options.CORK, true)
				.getMap());

		DefaultByteBufferPool bufferPool = new DefaultByteBufferPool(true, 8192 * 3, 1000, 10, 100);

		WampConnection conn = new UndertowWebSocketClientConnection(worker,bufferPool,new URI("ws://localhost:8080/ws"));

		WampClient client = new WampClient(conn);
		
		client.onOpen((session) -> {
			
			session.register("soma",(call)->{
				
				System.out.println(Thread.currentThread().getName());
				System.out.println("chamou");
				
				Integer p1 = call.params(0).as(Integer.class);
				Integer p2 = call.params(1).as(Integer.class);
				
				call.sendResult(p1+p2);
			});
			
			
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					System.out.println("faz call");
					session.call("soma",1,2).then((result)->{
						System.out.println(result);
						Long i = result.as(Long.class);
						System.out.println("O resultado da soma foi: " + i);
					});
				}
			};
			
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(timerTask,0,2000);
			
			
			System.out.println("connectou");
//			client.close();
//			System.exit(0);
		});

		client.open("realm1");
		

	}

}
