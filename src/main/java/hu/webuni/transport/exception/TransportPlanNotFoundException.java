package hu.webuni.transport.exception;

public class TransportPlanNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransportPlanNotFoundException(String errorMessage) {
	        super(errorMessage);
	    }
}
