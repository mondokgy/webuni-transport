package hu.webuni.transport.exception;

public class MilestoneNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MilestoneNotFoundException(String errorMessage) {
	        super(errorMessage);
	    }
}
