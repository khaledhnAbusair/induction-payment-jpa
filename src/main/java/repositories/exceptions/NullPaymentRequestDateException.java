package repositories.exceptions;

public class NullPaymentRequestDateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullPaymentRequestDateException() {
		super();
	}

	public NullPaymentRequestDateException(String message) {
		super(message);
	}

	public NullPaymentRequestDateException(Throwable cause) {
		super(cause);
	}

	public NullPaymentRequestDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullPaymentRequestDateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
