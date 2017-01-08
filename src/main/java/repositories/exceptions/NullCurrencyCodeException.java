package repositories.exceptions;

public class NullCurrencyCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullCurrencyCodeException() {
		super();
	}

	public NullCurrencyCodeException(String message) {
		super(message);
	}

	public NullCurrencyCodeException(Throwable cause) {
		super(cause);
	}

	public NullCurrencyCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullCurrencyCodeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
