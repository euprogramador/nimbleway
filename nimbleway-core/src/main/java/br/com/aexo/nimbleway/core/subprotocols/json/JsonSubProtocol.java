package br.com.aexo.nimbleway.core.subprotocols.json;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.DecoderMessage;
import br.com.aexo.nimbleway.core.subprotocols.EncoderMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;

public class JsonSubProtocol implements SubProtocol {

	@SuppressWarnings("rawtypes")
	private List<JsonDecoderMessage> decoders;

	@SuppressWarnings("rawtypes")
	private List<JsonEncoderMessage> encoders;

	public JsonSubProtocol() {

		this.decoders = Arrays.asList( //
				new AbortMessageJsonDecoder(), //
				new EventMessageJsonDecoder(), //
				new GoodByeMessageJsonDecoder(), //
				new InvocationMessageJsonDecoder(), //
				new PublishedMessageJsonDecoder(), //
				new RegisteredMessageJsonDecoder(), //
				new ReplyErrorMessageJsonDecoder(), //
				new ResultMessageJsonDecoder(), //
				new SubscribedMessageJsonDecoder(), //
				new UnregisteredMessageJsonDecoder(), //
				new UnsubscribedMessageJsonDecoder(), //
				new WelcomeMessageJsonDecoder() //
		);

		this.encoders = Arrays.asList(//
				new CallMessageJsonEncoder(), //
				new GoodByeMessageJsonEncoder(), //
				new HelloMessageJsonEncoder(), //
				new PublishMessageJsonEncoder(), //
				new RegisterMessageJsonEncoder(), //
				new SendErrorMessageJsonEncoder(), //
				new SubscribeMessageJsonEncoder(), //
				new UnregisterMessageJsonEncoder(), //
				new UnSubscribeMessageJsonEncoder(), //
				new YieldMessageJsonEncoder() //
		);

	}

	@Override
	public Object encode(WampMessage message) {
		List<EncoderMessage<WampMessage>> encoderFounded = encoders.stream()
				.filter((encoder) -> encoder.isEncodeOf(message)).collect(Collectors.toList());
		if (encoderFounded.size() == 0)
			throw new RuntimeException("nenhum encoder encontrado para a mensagem: " + message);
		return encoderFounded.get(0).encode(message);

	}

	@Override
	public WampMessage decode(Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode node = (ArrayNode) mapper.readTree(o.toString());
			List<DecoderMessage<WampMessage>> decoderFounded = decoders.stream()
					.filter((decoder) -> decoder.isDecodeOf(node.get(0).asInt())).collect(Collectors.toList());
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
