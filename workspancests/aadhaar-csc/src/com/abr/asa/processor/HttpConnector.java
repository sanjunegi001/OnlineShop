package com.abr.asa.processor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpConnector {
	public static String postData(String targetURL, String xmlData) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		if (targetURL.matches("^(https)://.*$")) {
			targetURL = a(targetURL, xmlData);
		} else {

			HttpURLConnection targetURL1;
			(targetURL1 = (HttpURLConnection) (new URL(targetURL)).openConnection()).setRequestMethod("POST");
			targetURL1.setRequestProperty("Content-Type", "application/xml");
			targetURL1.setRequestProperty("Content-Length", Integer.toString(xmlData.getBytes("UTF8").length));
			targetURL1.setRequestProperty("Content-Language", "en-US");
			targetURL1.setUseCaches(false);
			targetURL1.setDoInput(true);

			targetURL1.setDoOutput(true);
			DataOutputStream arg1;
			(arg1 = new DataOutputStream(targetURL1.getOutputStream())).writeBytes(xmlData);
			arg1.flush();
			BufferedReader xmlData1 = new BufferedReader(new InputStreamReader(targetURL1.getInputStream(), "UTF8"));
			StringBuffer targetURL2 = new StringBuffer();

			String arg5;
			while ((arg5 = xmlData1.readLine()) != null) {
				targetURL2.append(arg5);
			}

			targetURL = targetURL2.toString();

		}

		return targetURL;
	}

	private static String a(String arg, String arg0) throws IOException, NoSuchAlgorithmException, KeyManagementException {

		HttpsURLConnection arg1 = null;
		SSLContext arg2;
		(arg2 = SSLContext.getInstance("SSL")).init(new KeyManager[0], new TrustManager[] { new b() }, new SecureRandom());

		try {
			SSLSocketFactory arg11 = arg2.getSocketFactory();
			(arg1 = (HttpsURLConnection) (new URL(arg)).openConnection()).setRequestMethod("POST");
			arg1.setRequestProperty("Content-Type", "application/xml");
			arg1.setRequestProperty("Content-Length", Integer.toString(arg0.getBytes("UTF8").length));
			arg1.setRequestProperty("Content-Language", "en-US");
			arg1.setUseCaches(false);
			arg1.setDoInput(true);
			arg1.setDoOutput(true);
			arg1.setSSLSocketFactory(arg11);
			arg1.setHostnameVerifier(new a());
			DataOutputStream arg7;
			(arg7 = new DataOutputStream(arg1.getOutputStream())).writeBytes(arg0);
			arg7.flush();
			arg7.close();
			InputStream arg8 = arg1.getInputStream();
			BufferedReader arg10 = new BufferedReader(new InputStreamReader(arg8, "UTF8"));
			StringBuffer arg9 = new StringBuffer();

			String arg12;
			while ((arg12 = arg10.readLine()) != null) {
				arg9.append(arg12);
				arg9.append('\r');
			}
			System.out.println("arg9.toString()" + arg9.toString());

			arg = arg9.toString();
			return arg;
		} catch (RuntimeException arg5) {
			throw arg5;
		} finally {
			if (arg1 != null) {
				arg1.disconnect();
			}

		}
	}

	private static class a implements HostnameVerifier {
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}

	}

	private static class b implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}
	}

}
