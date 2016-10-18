package nimbleway.messages;


public class YieldMessage implements WampMessage {

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
