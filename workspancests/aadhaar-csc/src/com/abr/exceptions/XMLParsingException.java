package com.abr.exceptions;

public class XMLParsingException extends Exception {
	private String a;

	public XMLParsingException(String message, String exception) {
		this.a = exception;
	}

	public String getException() {
		return this.a;
	}
}