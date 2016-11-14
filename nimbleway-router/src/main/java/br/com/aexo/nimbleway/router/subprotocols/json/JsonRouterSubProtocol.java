package br.com.aexo.nimbleway.router.subprotocols.json;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.router.connection.RouterMessage;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterDecoderMessage;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterEncoderMessage;
import br.com.aexo.nimbleway.router.connection.subprotocols.RouterSubProtocol;
import br.com.aexo.nimbleway.router.subprotocols.json.decoder.HelloMessageJsonDecoder;
import br.com.aexo.nimbleway.router.subprotocols.json.encoder.WelcomeJsonEncoder;

public class JsonRouterSubProtocol implements RouterSubProtocol {

	@SuppressWarnings("rawtypes")
	private List<JsonDecoderMessage> decoders;

	@SuppressWarnings("rawtypes")
	private List<JsonEncoderMessage> encoders;

	public JsonRouterSubProtocol() {

		this.decoders = Arrays.asList( //
				new HelloMessageJsonDecoder());

		this.encoders = Arrays.asList(//
				new WelcomeJsonEncoder());

	}

	@Override
	public Object encode(RouterMessage message) {
		List<RouterEncoderMessage<RouterMessage>> encoderFounded = encoders.stream().filter((encoder) -> encoder.isEncodeOf(message)).collect(Collectors.toList());
		if (encoderFounded.size() == 0)
			throw new RuntimeException("nenhum encoder encontrado para a mensagem: " + message);
		return encoderFounded.get(0).encode(message);

	}

	@Override
	public RouterMessage decode(Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode node = (ArrayNode) mapper.readTree(o.toString());
			List<RouterDecoderMessage<RouterMessage>> decoderFounded = decoders.stream().filter((decoder) -> decoder.isDecodeOf(node.get(0).asInt())).collect(Collectors.toList());
			if (decoderFounded.size() == 0)
				throw new RuntimeException("nenhum decoder encontrado para a mensagem: " + o);
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
