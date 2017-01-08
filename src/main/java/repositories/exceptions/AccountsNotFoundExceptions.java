package repositories.exceptions;

public class AccountsNotFoundExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountsNotFoundExceptions() {
		// Nothing
	}

	public AccountsNotFoundExceptions(String message) {
		super(message);
	}

	public AccountsNotFoundExceptions(Throwable cause) {
		super(cause);
	}

	public AccountsNotFoundExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountsNotFoundExceptions(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
