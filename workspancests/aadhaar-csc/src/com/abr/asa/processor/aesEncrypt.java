package com.abr.asa.processor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class aesEncrypt {

	public static String encrypt(String data, String key) {
		byte[] a = key.getBytes();
		SecretKeySpec SecretKeysecretKey = new SecretKeySpec(a, "AES");

		byte[] cipherText = {};
		String ivs = "0000000000000000";
		SecureRandom SecureRandomrnd = new SecureRandom();
		IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
		try {
			Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, SecretKeysecretKey, iv);
			cipherText = encryptCipher.doFinal(data.getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("Exception in aesEncrypt() ====" + e);
		}
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String hashSHA256(String input) throws NoSuchAlgorithmException {
		MessageDigest MessageDigestmDigest = MessageDigest.getInstance("SHA-256");
		byte[] shaByteArr = MessageDigestmDigest.digest(input.getBytes());
		StringBuilder hexStrBuilder = new StringBuilder();
		for (int i = 0; i < shaByteArr.length; i++) {
			String hex = Integer.toHexString(0xFF & shaByteArr[i]);

			if (hex.length() == 1) {
				hexStrBuilder.append("0");
			}
			hexStrBuilder.append(hex);
		}
		return hexStrBuilder.toString();
	}

	public static String base64Encoded(String reXml) {
		byte[] authBytes = reXml.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(authBytes);
	}

}
