package br.com.aexo.nimbleway.subprotocols;

import br.com.aexo.nimbleway.DecoderMessage;
import br.com.aexo.nimbleway.WampMessage;
import br.com.aexo.nimbleway.messages.ResultMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class ResultMessageJsonDecoder implements DecoderMessage {

	@Override
	public WampMessage decode(Object o) {

		ArrayNode raw = (ArrayNode) o;
		Long idCall = raw.get(1).asLong();
		ArrayNode result = (ArrayNode) raw.get(3);

		return new ResultMessage(idCall,result.get(0));
	}

}
