package repositories.exceptions;

public class InvalidCurrencyCodeExpection extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCurrencyCodeExpection() {
		super();
	}

	public InvalidCurrencyCodeExpection(String message) {
		super(message);
	}

	public InvalidCurrencyCodeExpection(Throwable cause) {
		super(cause);
	}

	public InvalidCurrencyCodeExpection(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCurrencyCodeExpection(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
