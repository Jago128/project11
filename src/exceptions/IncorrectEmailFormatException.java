package exceptions;

public class IncorrectEmailFormatException extends Exception {
	private static final long serialVersionUID = 1L;

	public IncorrectEmailFormatException(String message) {
		super(message);
	}
	
}
