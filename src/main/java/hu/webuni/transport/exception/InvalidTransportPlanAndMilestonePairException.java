package hu.webuni.transport.exception;

public class InvalidTransportPlanAndMilestonePairException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidTransportPlanAndMilestonePairException(String errorMessage) {
	        super(errorMessage);
	    }
}
