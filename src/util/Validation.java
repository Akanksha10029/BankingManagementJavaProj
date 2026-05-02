package util;

import exceptions.ValidationException;

@FunctionalInterface
public interface Validation<String> {	
	/*@FunctionalInterface is an annotation that tells the interface must have exactly ONE abstract method.*/
	void validate(String value) throws ValidationException;
}
