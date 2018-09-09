package com.abr.asa.test.utils;

import java.io.File;
import java.io.FileInputStream;

public class Common {

	public static String AUA_CODE;
	public static String SUB_AUA_CODE;
	public static String AUA_LICENSE_KEY;
	public static String KUA_LICENSE_KEY;
	public static String UIDAI_PID_ENCRYPTION_CERTIFICATE;
	public static String UIDAI_VERIFY_CERTIFICATE;
	public static String AUA_SIGNING_PFX_CERTIFICATE;
	public static String AUA_SIGNING_PFX_CERTIFICATE_PASSWORD;
	public static String UDC;
	public static String ASA_URL;

	static {

		AUA_CODE = "STGABRPL01";
		SUB_AUA_CODE = "STGABRPL01";
		AUA_LICENSE_KEY = "MDiwZJticKCEyRzcBEPsM-VUcY-QIqCs9RlIuDLA9r3uog9Xb9zCTqc";
		KUA_LICENSE_KEY = "";
		UIDAI_PID_ENCRYPTION_CERTIFICATE = "./uidai_auth_encrypt_preprod.cer";
		UIDAI_VERIFY_CERTIFICATE = "./uidai_auth_sign_preprod.cer";
		/*
		 * AUA_SIGNING_PFX_CERTIFICATE = "./AuthBridge_Research_Services_Pvt_Ltd.pfx";
		 * AUA_SIGNING_PFX_CERTIFICATE_PASSWORD = "A11gt@!#dF&_Q";
		 */
		AUA_SIGNING_PFX_CERTIFICATE = "./eMudhraCertificate.pfx";
		AUA_SIGNING_PFX_CERTIFICATE_PASSWORD = "Auth1234";

		UDC = "AUT122333";
		ASA_URL = "http://115.111.25.104:8080/ECSAsaGatewayServerV2/AsaGateway";

	}

	public static byte[] readAll(String filename) throws Exception {
		File file = new File(filename);
		int length = (int) file.length();

		byte[] data = new byte[length];
		FileInputStream fis = new FileInputStream(file);
		fis.read(data);
		fis.close();

		return data;
	}

	public static String captureFMR(boolean kyc) throws Exception {
		return captureFMR(kyc, null);
	}

	public static String captureFMR(boolean kyc, String otp) throws Exception {

		/*
		 * Write code here to interface with RD Service for capturing biometrics and return PIDData XML
		 */

		return ""; // return actual PidData XML from RD service on capturing biometrics
	}
}
