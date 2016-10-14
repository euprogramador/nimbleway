package br.com.aexo.nimbleway;
/**
 * subprotocolos usados no processamento e conversão das mensagens
 * @author carlosr
 *
 */
public interface SubProtocol {

	String getName();

	DecoderMessage getDecoder();

	EncoderMessage getEncoder();

}
