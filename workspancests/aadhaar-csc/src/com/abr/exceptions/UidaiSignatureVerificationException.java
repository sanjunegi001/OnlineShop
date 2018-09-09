package com.abr.exceptions;

public class UidaiSignatureVerificationException extends Exception {
	private Exception a;

	public UidaiSignatureVerificationException(String message, Exception exception) {
		super(message);
		this.a = exception;
	}

	public Exception getException() {
		return this.a;
	}
}