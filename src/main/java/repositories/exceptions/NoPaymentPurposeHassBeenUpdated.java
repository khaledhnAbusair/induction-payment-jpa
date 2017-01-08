package repositories.exceptions;

public class NoPaymentPurposeHassBeenUpdated extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPaymentPurposeHassBeenUpdated() {
		super();
	}

	public NoPaymentPurposeHassBeenUpdated(String message) {
		super(message);
	}

	public NoPaymentPurposeHassBeenUpdated(Throwable cause) {
		super(cause);
	}

	public NoPaymentPurposeHassBeenUpdated(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPaymentPurposeHassBeenUpdated(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
