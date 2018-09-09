package com.abr.aua.pidgen.support;

public enum YesNo {
	Y, N;

	public final String value() {
		return name();
	}

	public static YesNo fromValue(String v) {
		return valueOf(v);
	}
}
