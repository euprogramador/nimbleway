package nimbleway.messages;


import com.fasterxml.jackson.databind.JsonNode;

public class ResultMessage implements WampMessage {

	private final JsonNode result;
	private final Long idCall;

	public ResultMessage(Long idCall, JsonNode result) {
		this.idCall = idCall;
		this.result = result;
	}

	public JsonNode getResult() {
		return result;
	}

	public Long getIdCall() {
		return idCall;
	}

}
