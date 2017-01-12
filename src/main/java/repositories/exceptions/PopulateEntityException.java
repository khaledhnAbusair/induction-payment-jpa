package repositories.exceptions;

public class PopulateEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PopulateEntityException() {
		super();
	}

	public PopulateEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PopulateEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public PopulateEntityException(String message) {
		super(message);
	}

	public PopulateEntityException(Throwable cause) {
		super(cause);
	}

}
