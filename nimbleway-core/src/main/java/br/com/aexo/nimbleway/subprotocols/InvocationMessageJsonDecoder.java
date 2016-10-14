package br.com.aexo.nimbleway.subprotocols;

import br.com.aexo.nimbleway.DecoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.InvocationMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Adicionar suporte a parametros nomeados
 * 
 * @author carlosr
 *
 */
public class InvocationMessageJsonDecoder implements DecoderMessage {

	@Override
	public WampMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		Long idRequest = raw.get(1).asLong();
		Long idFunctionRegistration = raw.get(2).asLong();
		ArrayNode params = (ArrayNode) raw.get(4);
		return new InvocationMessage(idRequest, idFunctionRegistration, params);
	}

}
