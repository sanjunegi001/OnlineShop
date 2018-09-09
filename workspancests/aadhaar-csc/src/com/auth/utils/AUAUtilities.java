/*
 * 
 */
package com.auth.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class AUAUtilities.
 */
public class AUAUtilities {

	private final static String TRANSFORMATION = "AES";
	private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";

	private static int CIPHER_KEY_LEN = 16; // 128 bits

	/**
	 * * Getting IP Address.
	 *
	 * @param request
	 *            the request
	 * @return the client ip addr
	 */

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * *** generated the unique id .
	 *
	 * @return the int
	 */
	public static BigInteger generateUniqueId() {

		int randomPIN = (int) (Math.random() * 9000) + 1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String txn = "" + randomPIN + dateFormat.format(new Date());
		BigInteger number = new BigInteger(txn);
		return number;

	}

	/**
	 * * Generated The Unique Id For BioMatric Request *.
	 *
	 * @return the int
	 */

	public static int biogenerateUniqueId() {
		UUID idBioOne = UUID.randomUUID();
		String strbio = "" + idBioOne;
		int uid = strbio.hashCode();
		String filterbioStr = "" + uid;
		strbio = filterbioStr.replaceAll("-", "");
		return Integer.parseInt(strbio);
	}

	public static String doDecrypt(String encodekey, String encrptedStr) {

		try {

			byte sha[] = generateSha512Hash(encodekey.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < sha.length; i++) {
				sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16).substring(1));
			}
			String shaKey = sb.substring(0, 16);
			String[] parts = encrptedStr.split(":");
			IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
			SecretKeySpec secretKey = new SecretKeySpec(shaKey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance(AUAUtilities.CIPHER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);

			byte[] original = cipher.doFinal(decodedEncryptedData);

			return new String(original);

		} catch (Exception e) {
			return "A900";

		}

	}

	public static byte[] generateSha512Hash(byte[] data) {

		String algorithm = "SHA-512";

		byte[] hash = null;

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			hash = digest.digest(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;

	}

	public static String doEncrypt(String key, String iv, String data) {
		try {
			byte[] sha = generateSha512Hash(key.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < sha.length; i++) {
				sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16).substring(1));
			}
			String shaKey = sb.substring(0, 16);

			IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
			SecretKeySpec secretKey = new SecretKeySpec(fixKey(shaKey).getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance(AUAUtilities.CIPHER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

			byte[] encryptedData = cipher.doFinal((data.getBytes()));

			String encryptedDataInBase64 = Base64.getEncoder().encodeToString(encryptedData);
			String ivInBase64 = Base64.getEncoder().encodeToString(iv.getBytes("UTF-8"));

			return encryptedDataInBase64 + ":" + ivInBase64;

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String fixKey(String key) {

		if (key.length() < AUAUtilities.CIPHER_KEY_LEN) {
			int numPad = AUAUtilities.CIPHER_KEY_LEN - key.length();

			for (int i = 0; i < numPad; i++) {
				key += "0"; // 0 pad to len 16 bytes
			}

			return key;

		}

		if (key.length() > AUAUtilities.CIPHER_KEY_LEN) {
			return key.substring(0, CIPHER_KEY_LEN); // truncate to 16 bytes
		}

		return key;
	}

	public static String maskCardNumber(String cardNumber, String mask) {

		// format the number
		int index = 0;
		StringBuilder maskedNumber = new StringBuilder();
		for (int i = 0; i < mask.length(); i++) {
			char c = mask.charAt(i);
			if (c == '#') {
				maskedNumber.append(cardNumber.charAt(index));
				index++;
			} else if (c == 'x') {
				maskedNumber.append(c);
				index++;
			} else {
				maskedNumber.append(c);
			}
		}

		// return the masked number
		return maskedNumber.toString();
	}

}
