package br.com.aexo.nimbleway.subprotocols.json.decoder;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.ResultMessage;
import br.com.aexo.nimbleway.subprotocols.json.JsonDecoderMessage;

import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class ResultMessageJsonDecoder implements JsonDecoderMessage<ResultMessage> {

	@Override
	public ResultMessage decode(Object o) {
		ArrayNode raw = (ArrayNode) o;
		Long idCall = raw.get(1).asLong();
		ArrayNode result = (ArrayNode) raw.get(3);

		return new ResultMessage(idCall, result.get(0));
	}

	@Override
	public boolean isDecodeOf(Integer messageIdType) {
		return new Integer(50).equals(messageIdType);
	}

}
