package repositories.exceptions;

public class CurrenciesNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CurrenciesNotFoundException() {
		super();
	}

	public CurrenciesNotFoundException(String message) {
		super(message);
	}

	public CurrenciesNotFoundException(Throwable cause) {
		super(cause);
	}

	public CurrenciesNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CurrenciesNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
