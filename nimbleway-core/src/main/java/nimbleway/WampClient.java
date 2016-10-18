package nimbleway;

import java.util.Collection;
import java.util.function.Consumer;

import nimbleway.subprotocols.SubProtocol;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;


public class WampClient {

	private WampConnection connection;

	private Consumer<WampSession> onOpenCallback = (session) -> {
	};

	public WampClient(WampConnection connection) {
		this.connection = connection;
	}

	public void onOpen(Consumer<WampSession> onOpenCallback) {
		this.onOpenCallback = onOpenCallback;
	}

	// ((ConfigurableApplicationContext)appCtx).close();
	public void open(String realm) {
		
		// context for subprotocol
		ApplicationContext subProtocolContext = new AnnotationConfigApplicationContext("nimbleway.subprotocols", "nimbleway.messages");
		Collection<SubProtocol> supportedSubProtocols = subProtocolContext.getBeansOfType(SubProtocol.class).values();
		
		connection.onOpen((transport)->{
			// register custom beans for context for connection
			GenericApplicationContext runtimeBeans = new GenericApplicationContext();
			ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) runtimeBeans).getBeanFactory();
			beanFactory.registerSingleton(WampTransport.class.getCanonicalName(),transport);
			runtimeBeans.refresh();

			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { this.getClass().getPackage().getName().replaceAll("\\.", "/")+ "/beans.xml" }, runtimeBeans);
			WampClientHandshake clientHandshake = context.getBean(WampClientHandshake.class);
			
			clientHandshake.onHandshake((session)->{
				onOpenCallback.accept(session);
			});
			clientHandshake.handshake(realm);
		});
		
		connection.open(supportedSubProtocols);
	}

	public void close() {
		connection.close();
	}

	
}
