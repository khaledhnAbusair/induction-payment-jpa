package repositories.exceptions;

public class AccountHasNoPaymentRequestsDueThisDayException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AccountHasNoPaymentRequestsDueThisDayException() {
		super();
	}

	public AccountHasNoPaymentRequestsDueThisDayException(String message) {
		super(message);
	}

	public AccountHasNoPaymentRequestsDueThisDayException(Throwable cause) {
		super(cause);
	}

	public AccountHasNoPaymentRequestsDueThisDayException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountHasNoPaymentRequestsDueThisDayException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
