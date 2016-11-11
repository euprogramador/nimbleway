package br.com.aexo.nimbleway.client;

import br.com.aexo.nimbleway.core.MessageType;

class WaitReplyKey {

	private MessageType type;
	private Long requestId;

	public WaitReplyKey(MessageType type, Long requestId) {
		super();
		this.type = type;
		this.requestId = requestId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestId == null) ? 0 : requestId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WaitReplyKey other = (WaitReplyKey) obj;
		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
