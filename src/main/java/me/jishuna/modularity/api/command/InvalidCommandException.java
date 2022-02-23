package me.jishuna.modularity.api.command;

public class InvalidCommandException extends RuntimeException {
	private static final long serialVersionUID = -1012231238197694599L;

	public InvalidCommandException(String message) {
		super(message);
	}

}
