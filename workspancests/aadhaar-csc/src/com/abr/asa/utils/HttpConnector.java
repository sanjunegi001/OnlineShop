package com.abr.asa.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
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

	public static class DummyTrustManager implements X509TrustManager {

		public DummyTrustManager() {
		}

		public boolean isClientTrusted(X509Certificate cert[]) {
			return true;
		}

		public boolean isServerTrusted(X509Certificate cert[]) {
			return true;
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}
	}

	public static class DummyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String urlHostname, String certHostname) {
			return true;
		}

		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
	}

	public static String getResponseFromHttps(String asaUrl, String xml) {

		SSLContext sslcontext = null;
		String responseXML = "";
		try {

			sslcontext = SSLContext.getInstance("TLS");

			/* sslcontext = SSLContext.getInstance("SSL"); */

			sslcontext.init(new KeyManager[0], new TrustManager[] { new DummyTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {

		} catch (KeyManagementException e) {
		}

		String urlParameters = "eXml=";
		try {
			urlParameters = urlParameters + URLEncoder.encode(xml, "UTF-8");
		} catch (Exception e) {

		}
		try {
			SSLSocketFactory factory = sslcontext.getSocketFactory();
			URL url;
			HttpsURLConnection connection;
			InputStream is = null;

			String ip = asaUrl;
			url = new URL(ip);
			System.out.println("URL " + ip);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(factory);
			connection.setHostnameVerifier(new DummyHostnameVerifier());
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(urlParameters);
			osw.flush();
			osw.close();
			is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String strTemp;
			while ((strTemp = in.readLine()) != null) {
				responseXML = responseXML + strTemp;
				// System.out.println(responseXML);
			}

			// System.out.println("Output: "+line);
			is.close();
			in.close();
		} catch (ConnectException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseXML;
	}
	// added by Manju for licence key check

	public static String getResponseFromHttps(String httpsUrl, String postParName, String postParValue) {
		// Log.kua.debug("In getResponseFromHttps method of ConnectHttps");
		SSLContext sslcontext = null;
		String responseXML = "";
		try {
			// sslcontext = SSLContext.getInstance("SSL");
			sslcontext = SSLContext.getInstance("TLS");

			sslcontext.init(new KeyManager[0], new TrustManager[] { new DummyTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

		} catch (KeyManagementException e) {

			e.printStackTrace();
		}

		String urlParameters = postParName + "=";
		try {
			urlParameters = urlParameters + URLEncoder.encode(postParValue, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();

		}
		try {
			SSLSocketFactory factory = sslcontext.getSocketFactory();
			URL url;
			HttpsURLConnection connection;
			InputStream is = null;

			String ip = httpsUrl;
			url = new URL(ip);

			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(factory);
			connection.setHostnameVerifier(new DummyHostnameVerifier());
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(urlParameters);
			osw.flush();
			osw.close();
			is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String strTemp;
			while ((strTemp = in.readLine()) != null) {
				responseXML = strTemp;
			}
			is.close();
			in.close();
		} catch (ConnectException e) {
			e.printStackTrace();
			responseXML = "Unable to connect to server";
		} catch (Exception e) {
			e.printStackTrace();
			responseXML = "Exception Occured";
		}
		return responseXML;
	}

}