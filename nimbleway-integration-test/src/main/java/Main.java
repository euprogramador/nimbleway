import io.undertow.server.DefaultByteBufferPool;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import nimbleway.UndertowWebSocketClientConnection;

import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Xnio;
import org.xnio.XnioWorker;

import br.com.aexo.nimbleway.WampClient;
import br.com.aexo.nimbleway.WampConnection;



public class Main {

	public static void main(String[] args) throws Exception {
		   System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

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
		
		client.onException((e)->{
			System.out.println(e);
			throw new RuntimeException(e);
		});
		
		client.onOpen((session) -> {
			session.subscribe("com.myapp.mytopic1",(call)->{
				System.out.println("invocado");
			});
//			
//			session.register("soma",(call)->{
//				Integer p1 = call.params(0).as(Integer.class);
//				Integer p2 = call.params(1).as(Integer.class);
//				call.sendResult(p1+p2);
//			});
			
			
			
			Timer timer = new Timer();
			
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					
					session.publish("com.myapp.mytopic1","hello").done((r)->{
						System.out.println("publicado");
					});
					
//					session.call("soma",1,2).then((result)->{
//						Long i = result.as(Long.class);
//						System.out.println("O resultado da soma foi: " + i);
//						timer.cancel();
//						session.close();
//					});
				}
			};
			
			
			timer.schedule(timerTask, 5000);
		});

		client.open("realm1");
		

	}

}
