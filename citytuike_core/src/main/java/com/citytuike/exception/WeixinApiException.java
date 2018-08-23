package com.citytuike.exception;

public class WeixinApiException extends Throwable {

	private static final long serialVersionUID = -5731201514409299289L;

	public WeixinApiException() {
	}

	public WeixinApiException(String message) {
		super(message);
	}

	public WeixinApiException(Throwable cause) {
		super(cause);
	}

	public WeixinApiException(String message, Throwable cause) {
		super(message, cause);
	}

}
