package br.com.aexo.nimbleway.subprotocols.json;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.aexo.nimbleway.messages.WampMessage;
import br.com.aexo.nimbleway.subprotocols.DecoderMessage;
import br.com.aexo.nimbleway.subprotocols.EncoderMessage;
import br.com.aexo.nimbleway.subprotocols.SubProtocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class JsonSubProtocol implements SubProtocol {

	@SuppressWarnings("rawtypes")
	private List<JsonDecoderMessage> decoders;
	
	@SuppressWarnings("rawtypes")
	private List<JsonEncoderMessage> encoders;

	@SuppressWarnings("rawtypes")
	public JsonSubProtocol(List<JsonEncoderMessage> encoders,List<JsonDecoderMessage> decoders) {
		this.encoders = encoders;
		this.decoders = decoders;
	}
	
	@Override
	public Object encode(WampMessage message) {
		List<EncoderMessage<WampMessage>> encoderFounded = encoders.stream().filter((encoder)->encoder.isEncodeOf(message)).collect(Collectors.toList());
		if (encoderFounded.size()==0) throw new RuntimeException("nenhum encoder encontrado para a mensagem: "+ message);
		return encoderFounded.get(0).encode(message);

	}

	@Override
	public WampMessage decode(Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode node = (ArrayNode) mapper.readTree(o.toString());
			List<DecoderMessage<WampMessage>> decoderFounded = decoders.stream().filter((decoder)->decoder.isDecodeOf(node.get(0).asInt())).collect(Collectors.toList());
			if (decoderFounded.size()==0) throw new RuntimeException("nenhum decoder encontrado para a mensagem: "+ o);
			return decoderFounded.get(0).decode(node);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String getName() {
		return "wamp.2.json";
	}

}
