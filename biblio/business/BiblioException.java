package biblio.business;

public class BiblioException extends Exception {

	public BiblioException() {
		super();
		
	}

	public BiblioException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BiblioException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BiblioException(String message) {
		super(message);
		
	}

	public BiblioException(Throwable cause) {
		super(cause);
		
	}
	
}
