package exceptions;

public class AccountNotFoundException extends RuntimeException {
	// use this cutom exception in code like serviceImpl class
	// constructor
	public AccountNotFoundException(String message) {
		super(message);
	}	
}