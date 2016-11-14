package br.com.aexo.nimbleway.core.subprotocols.json;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.aexo.nimbleway.core.messages.WampMessage;
import br.com.aexo.nimbleway.core.subprotocols.DecoderMessage;
import br.com.aexo.nimbleway.core.subprotocols.EncoderMessage;
import br.com.aexo.nimbleway.core.subprotocols.SubProtocol;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.AbortMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.EventMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.GoodByeMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.GoodByeMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.HelloMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.InvocationMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.PublishedMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.RegisterMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.RegisteredMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.ReplyErrorMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.ResultMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.SubscribedMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.UnregisteredMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.UnsubscribedMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.decoder.WelcomeMessageJsonDecoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.CallMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.HelloMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.PublishMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.RegisterMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.RegisteredMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.SendErrorMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.SubscribeMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.UnSubscribeMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.UnregisterMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.WelcomeMessageJsonEncoder;
import br.com.aexo.nimbleway.core.subprotocols.json.encoder.YieldMessageJsonEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
				new HelloMessageJsonDecoder(), //
				new WelcomeMessageJsonDecoder(), //
				new RegisterMessageJsonDecoder()
				);

		this.encoders = Arrays.asList(//
				new CallMessageJsonEncoder(), //
				new GoodByeMessageJsonEncoder(), //
				new PublishMessageJsonEncoder(), //
				new RegisterMessageJsonEncoder(), //
				new SendErrorMessageJsonEncoder(), //
				new SubscribeMessageJsonEncoder(), //
				new UnregisterMessageJsonEncoder(), //
				new UnSubscribeMessageJsonEncoder(), //
				new WelcomeMessageJsonEncoder(),
				new YieldMessageJsonEncoder(), //
				new HelloMessageJsonEncoder(), //
				new RegisteredMessageJsonEncoder()
				
				);

	}

	@Override
	public Object encode(WampMessage message) {
		List<EncoderMessage<WampMessage>> encoderFounded = encoders.stream().filter((encoder) -> encoder.isEncodeOf(message)).collect(Collectors.toList());
		if (encoderFounded.size() == 0)
			throw new RuntimeException("nenhum encoder encontrado para a mensagem: " + message);
		return encoderFounded.get(0).encode(message);

	}

	@Override
	public WampMessage decode(Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode node = (ArrayNode) mapper.readTree(o.toString());
			List<DecoderMessage<WampMessage>> decoderFounded = decoders.stream().filter((decoder) -> decoder.isDecodeOf(node.get(0).asInt())).collect(Collectors.toList());
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
