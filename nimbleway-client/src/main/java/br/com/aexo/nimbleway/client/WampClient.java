package br.com.aexo.nimbleway.client;

import java.util.Collection;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import br.com.aexo.nimbleway.core.WampConnection;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;

/**
 * class responsible for client functionality
 * 
 * @author carlosr
 *
 */
public class WampClient {

	private static Logger log = LoggerFactory.getLogger(WampClient.class);

	private WampConnection connection;

	private Consumer<ClientSession> onOpenCallback = (session) -> {
	};

	private Consumer<Exception> exceptionHandler = (Exception) -> {
	};

	/**
	 * create a wamp client instance using a WampConnection
	 * 
	 * @param connection
	 */
	public WampClient(WampConnection connection) {
		this.connection = connection;
	}

	/**
	 * callback called of wamp handshake complete successiful on router
	 * 
	 * @param onOpenCallback
	 */
	public void onOpen(Consumer<ClientSession> onOpenCallback) {
		this.onOpenCallback = onOpenCallback;
	}

	/**
	 * connect to router in especific realm
	 * 
	 * @param realm
	 */
	// ((ConfigurableApplicationContext)appCtx).close();
	@SuppressWarnings("resource")
	public void open(String realm) {
		log.debug("open connection to router");

		// context for subprotocol
		ApplicationContext subProtocolContext = new AnnotationConfigApplicationContext(SubProtocol.class.getPackage().getName(), WampMessage.class.getPackage().getName());
		Collection<SubProtocol> supportedSubProtocols = subProtocolContext.getBeansOfType(SubProtocol.class).values();
		log.trace("subprotocol context created " + subProtocolContext);
		connection.onException(exceptionHandler);

		connection.onOpen((transport) -> {
			// register custom beans for context for connection
				GenericApplicationContext runtimeBeans = new GenericApplicationContext();
				ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) runtimeBeans).getBeanFactory();
				beanFactory.registerSingleton(WampTransport.class.getCanonicalName(), transport);
				runtimeBeans.refresh();
				
				log.trace("runtime beans context created");
				

				ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/beans.xml" }, runtimeBeans);
				WampClientHandshake clientHandshake = context.getBean(WampClientHandshake.class);

				log.trace("application context created");
				
				clientHandshake.onHandshake((session) -> {
					onOpenCallback.accept(session);
				});
				
				clientHandshake.onException(exceptionHandler);
				
				clientHandshake.handshake(realm);
			});

		
		connection.open(supportedSubProtocols);
	}

	public void onException(Consumer<Exception> exceptionHandler) {
		log.trace("configured on exception handler");
		this.exceptionHandler = exceptionHandler;
	}

}
