package learn.reflection;

public class DelegationException extends RuntimeException{

	private static final long serialVersionUID = 3354909903961615397L;
	
	public DelegationException(String message) {
		super(message);
	}
	
	public DelegationException(Throwable cause){
		super(cause);
	}
	
	public DelegationException(String message,Throwable cause){
		super(message, cause);
	}
}
