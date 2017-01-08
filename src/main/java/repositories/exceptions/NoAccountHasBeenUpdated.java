package repositories.exceptions;

public class NoAccountHasBeenUpdated extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoAccountHasBeenUpdated() {
		super();
	}

	public NoAccountHasBeenUpdated(String message) {
		super(message);
	}

	public NoAccountHasBeenUpdated(Throwable cause) {
		super(cause);
	}

	public NoAccountHasBeenUpdated(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAccountHasBeenUpdated(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
