package repositories.exceptions;

public class AccountIsAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountIsAlreadyExistException() {
		super();
	}

	public AccountIsAlreadyExistException(String message) {
		super(message);
	}

	public AccountIsAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public AccountIsAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountIsAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
