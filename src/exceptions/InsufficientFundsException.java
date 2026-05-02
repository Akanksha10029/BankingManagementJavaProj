package exceptions;

public class InsufficientFundsException extends RuntimeException{
	/**
	 * @param message
	 */
	public InsufficientFundsException(String message) {
		super(message);
	}
	
}
