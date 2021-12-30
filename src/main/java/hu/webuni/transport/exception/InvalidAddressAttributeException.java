package hu.webuni.transport.exception;

public class InvalidAddressAttributeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidAddressAttributeException(String errorMessage) {
	        super(errorMessage);
	    }
}
