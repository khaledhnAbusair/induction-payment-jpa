package repositories.exceptions;

public class AccountNotFoundExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountNotFoundExceptions() {
		// Nothing
	}

	public AccountNotFoundExceptions(String message) {
		super(message);
	}

	public AccountNotFoundExceptions(Throwable cause) {
		super(cause);
	}

	public AccountNotFoundExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotFoundExceptions(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
