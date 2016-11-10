package br.com.aexo.nimbleway.core.messages;

public class GoodByeMessage implements WampMessage {

	private boolean waitReplyRouter;

	public GoodByeMessage(boolean waitReplyRouter) {
		this.waitReplyRouter = waitReplyRouter;
	}


	public boolean isWaitReplyRouter() {
		return waitReplyRouter;
	}
}