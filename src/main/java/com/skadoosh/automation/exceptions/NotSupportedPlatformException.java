package com.skadoosh.automation.exceptions;

public class NotSupportedPlatformException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotSupportedPlatformException() {
		super("Platform type requested is not supported.");
	}

	public NotSupportedPlatformException(final String errorMessage) {
		super(errorMessage);
	}

}
