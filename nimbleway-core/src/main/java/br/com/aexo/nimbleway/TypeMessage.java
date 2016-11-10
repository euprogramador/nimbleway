package br.com.aexo.nimbleway;

public enum TypeMessage {

	SUBSCRIBE(32), UNSUBSCRIBE(34), PUBLISH(16), //
	REGISTER(64), UNREGISTER(66), CALL(48), INVOCATION(68);

	private Integer id;

	private TypeMessage(Integer type) {
		this.id = type;
	}

	public static TypeMessage forType(Integer typeId) {

		TypeMessage[] types = TypeMessage.values();

		for (TypeMessage type : types)
			if (type.id.equals(typeId))
				return type;
		throw new IllegalArgumentException("type is not detected: " + typeId);
	}

	public Integer getId() {
		return id;
	}

}
