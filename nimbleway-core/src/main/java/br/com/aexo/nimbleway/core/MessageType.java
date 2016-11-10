package br.com.aexo.nimbleway.core;

public enum MessageType {

	SUBSCRIBE(32), UNSUBSCRIBE(34), PUBLISH(16), REGISTER(64), UNREGISTER(66), CALL(48), INVOCATION(68);

	private Integer id;

	private MessageType(Integer type) {
		this.id = type;
	}

	public static MessageType forType(Integer typeId) {

		MessageType[] types = MessageType.values();

		for (MessageType type : types)
			if (type.id.equals(typeId))
				return type;
		throw new IllegalArgumentException("type is not detected: " + typeId);
	}

	public Integer getId() {
		return id;
	}

}
