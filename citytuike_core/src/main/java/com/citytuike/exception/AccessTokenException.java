package com.citytuike.exception;


public class AccessTokenException extends WeixinApiException {

	private static final long serialVersionUID = 1526515891555603623L;

	public AccessTokenException() {
		super();
	}

	public AccessTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTokenException(String message) {
		super(message);
	}

	public AccessTokenException(Throwable cause) {
		super(cause);
	}

}
