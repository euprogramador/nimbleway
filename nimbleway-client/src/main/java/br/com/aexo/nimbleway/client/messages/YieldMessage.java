package br.com.aexo.nimbleway.client.messages;

import br.com.aexo.nimbleway.client.interaction.Result;


public class YieldMessage implements ClientMessage {

	private InvocationMessage inReplyTo;
	private Result reply;

	public YieldMessage(InvocationMessage inReplyTo, Result reply) {
		this.inReplyTo = inReplyTo;
		this.reply = reply;
	}

	public InvocationMessage getInReplyTo() {
		return inReplyTo;
	}

	public Result getReply() {
		return reply;
	}

}
