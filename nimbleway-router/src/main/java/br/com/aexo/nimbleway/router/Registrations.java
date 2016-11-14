package br.com.aexo.nimbleway.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.aexo.nimbleway.core.Registration;
import br.com.aexo.nimbleway.core.WampTransport;
import br.com.aexo.nimbleway.utils.SpinLock;

public class Registrations {

	private SpinLock spinLock = new SpinLock();
	private Map<String, ServerRegistrations> registers = new HashMap<>();

	public void save(long id, WampTransport transport, Registration registration) {
		spinLock.lock();

		ServerRegistrations register = null;
		if (registers.containsKey(registration.getName())) {
			register = registers.get(registration.getName());
		} else {
			register = new ServerRegistrations();
			registers.put(registration.getName(), register);
		}
		register.save(new ServerRegistration(id, transport, registration));

		spinLock.unlock();
	}

	class ServerRegistrations {
		String name;
		List<ServerRegistration> registrations = new ArrayList<>();
		Integer index = 0;

		public void save(ServerRegistration serverRegistration) {
			registrations.add(serverRegistration);
		}
	}

	class ServerRegistration {

		private Long id;
		private WampTransport transport;
		private Registration registration;

		public ServerRegistration(Long id, WampTransport transport, Registration registration) {
			super();
			this.id = id;
			this.transport = transport;
			this.registration = registration;
		}

		public Long getId() {
			return id;
		}

		public WampTransport getTransport() {
			return transport;
		}

		public Registration getRegistration() {
			return registration;
		}

	}

}
