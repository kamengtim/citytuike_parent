package com.citytuike.exception;


public class PermissionException extends WeixinApiException {

	private static final long serialVersionUID = -3316976851630862001L;

	public PermissionException() {
	}

	public PermissionException(String message) {
		super(message);
	}

	public PermissionException(Throwable cause) {
		super(cause);
	}

	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}

}
