package repositories.exceptions;

public class IbanNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IbanNotExistException() {
		// No Arguments Constructor must exist
	}

	public IbanNotExistException(String message) {
		super(message);
	}

	public IbanNotExistException(Throwable cause) {
		super(cause);
	}

	public IbanNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public IbanNotExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
