package com.abr.exceptions;

public class PidServerException extends Exception {

	private String a;

	public PidServerException(String message) {
		super(message);

	}

	public String getCode() {
		return this.a;
	}

	public void setCode(String code) {
		this.a = code;
	}

}
