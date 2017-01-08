package repositories.exceptions;

public class AccountHasNoPaymentRequestsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountHasNoPaymentRequestsException() {
		super();
	}

	public AccountHasNoPaymentRequestsException(String message) {
		super(message);
	}

	public AccountHasNoPaymentRequestsException(Throwable cause) {
		super(cause);
	}

	public AccountHasNoPaymentRequestsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountHasNoPaymentRequestsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
