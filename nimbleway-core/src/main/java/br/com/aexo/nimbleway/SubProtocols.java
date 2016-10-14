package br.com.aexo.nimbleway;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.aexo.nimbleway.subprotocols.JsonSubProtocol;

public class SubProtocols {

	private Map<String,SubProtocol> subprotocols = new HashMap<String, SubProtocol>();
	
	public SubProtocols(){
		subprotocols.put("wamp.2.json",new JsonSubProtocol());
	}

	
	public SubProtocol getSubProtocol(String name) {
	//TODO lançar exception quando o subprotocol não suportado
		return subprotocols.get(name);
	}


	public Set<String> supportedProtocols() {
		return subprotocols.keySet();
	}

}
