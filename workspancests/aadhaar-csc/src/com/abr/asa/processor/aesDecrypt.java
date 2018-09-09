package com.abr.asa.processor;

import java.io.StringReader;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public class aesDecrypt {

	public static String decryptText(String data, String key) {

		byte[] byteCipherText = Base64.getDecoder().decode(data);
		byte[] a = key.getBytes();
		byte[] bytePlainText = {};
		String ivs = "0000000000000000";
		SecretKeySpec SecretKeysecretKey = new SecretKeySpec(a, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
		try {
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipher.init(Cipher.DECRYPT_MODE, SecretKeysecretKey, iv);
			bytePlainText = aesCipher.doFinal(byteCipherText);
		} catch (Exception e) {
			System.out.println("Exception in decryptText() ====" + e);
		}
		return new String(bytePlainText);
	}

	public static <T> T base64Decoded(Class<T> clazz, String xml) throws JAXBException, XMLStreamException {

		byte[] decodedBytes = Base64.getDecoder().decode(xml);
		String decodedString = new String(decodedBytes);

		System.out.println("decodedString" + decodedString);

		return (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(new StringReader(decodedString));
	}

}
