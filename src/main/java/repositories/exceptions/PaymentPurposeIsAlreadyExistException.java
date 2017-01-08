package repositories.exceptions;

public class PaymentPurposeIsAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PaymentPurposeIsAlreadyExistException() {
		super();
	}

	public PaymentPurposeIsAlreadyExistException(String message) {
		super(message);
	}

	public PaymentPurposeIsAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public PaymentPurposeIsAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentPurposeIsAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
