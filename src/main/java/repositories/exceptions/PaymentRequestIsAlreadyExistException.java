package repositories.exceptions;

public class PaymentRequestIsAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PaymentRequestIsAlreadyExistException() {
		super();
	}

	public PaymentRequestIsAlreadyExistException(String message) {
		super(message);
	}

	public PaymentRequestIsAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public PaymentRequestIsAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentRequestIsAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
