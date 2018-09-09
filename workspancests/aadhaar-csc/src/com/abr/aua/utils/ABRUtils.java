package com.abr.aua.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ABRUtils {
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] array = new byte[4096];
		long n = 0L;
		int read;
		while (-1 != (read = input.read(array))) {
			byteArrayOutputStream.write(array, 0, read);
			n += read;
		}
		if (n > 2147483647L) {
			throw new IOException();
		}
		return byteArrayOutputStream.toByteArray();
	}

	public static boolean isNumeric(String str) {
		char[] charArray;
		for (int length = (charArray = str.toCharArray()).length, i = 0; i < length; ++i) {
			if (!Character.isDigit(charArray[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNullOrEmpty(String str) {
		boolean b;
		return b = (str == null || str.trim().length() == 0);
	}
}
