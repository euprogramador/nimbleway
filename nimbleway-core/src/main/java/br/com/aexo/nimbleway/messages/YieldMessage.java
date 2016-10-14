package br.com.aexo.nimbleway.messages;

import br.com.aexo.nimbleway.WampMessage;

public class YieldMessage extends WampMessage {

	private InvocationMessage inReplyTo;
	private Object reply;

	public YieldMessage(InvocationMessage inReplyTo, Object reply) {
		this.inReplyTo = inReplyTo;
		this.reply = reply;
	}

	public InvocationMessage getInReplyTo() {
		return inReplyTo;
	}

	public Object getReply() {
		return reply;
	}


}
