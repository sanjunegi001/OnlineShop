package com.abr.exceptions;

public class AuaServerException extends Exception {
	private String a;

	public AuaServerException(String code, String message) {
		super(message);
		this.a = code;
	}

	public String getCode() {
		return this.a;
	}

	public void setCode(String code) {
		this.a = code;
	}
}