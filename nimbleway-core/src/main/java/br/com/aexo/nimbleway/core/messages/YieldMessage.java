package br.com.aexo.nimbleway.core.messages;

import br.com.aexo.nimbleway.core.Result;


public class YieldMessage implements WampMessage {

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
