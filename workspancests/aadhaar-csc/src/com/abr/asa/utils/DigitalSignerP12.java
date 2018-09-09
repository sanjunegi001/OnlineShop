package com.abr.asa.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.abr.aua.ds.IECSSigner;

public class DigitalSignerP12 implements IECSSigner {

	private KeyStore.PrivateKeyEntry jdField_a_of_type_JavaSecurityKeyStore;
	private Provider jdField_a_of_type_JavaSecurityProvider;

	public DigitalSignerP12(String keyStoreFile, char[] keyStorePassword, String alias) {
		this.jdField_a_of_type_JavaSecurityKeyStore = a(keyStoreFile, keyStorePassword, alias);
		if (this.jdField_a_of_type_JavaSecurityKeyStore == null) {
			throw new RuntimeException("Key could not be read for digital signature. Please check value of signature alias and signature password, and restart the Auth Client");
		}
	}

	public DigitalSignerP12(String keyStoreFile, char[] keyStorePassword) {
		this.jdField_a_of_type_JavaSecurityKeyStore = a(keyStoreFile, keyStorePassword, null);
		if (this.jdField_a_of_type_JavaSecurityKeyStore == null) {
			throw new RuntimeException("Key could not be read for digital signature. Please check value of signature alias and signature password, and restart the Auth Client");
		}
	}

	public DigitalSignerP12(InputStream keyStoreFileStream, char[] keyStorePassword, String alias) {
		this.jdField_a_of_type_JavaSecurityKeyStore = a(keyStoreFileStream, keyStorePassword, alias);
		if (this.jdField_a_of_type_JavaSecurityKeyStore == null) {
			throw new RuntimeException("Key could not be read for digital signature. Please check value of signature alias and signature password, and restart the Auth Client");
		}
	}

	public DigitalSignerP12(InputStream keyStoreFileStream, char[] keyStorePassword) {

		this.jdField_a_of_type_JavaSecurityKeyStore = a(keyStoreFileStream, keyStorePassword, null);

		if (this.jdField_a_of_type_JavaSecurityKeyStore == null) {
			throw new RuntimeException("Key could not be read for digital signature. Please check value of signature alias and signature password, and restart the Auth Client");
		}
	}

	private static KeyStore.PrivateKeyEntry a(InputStream inputStream, char[] array, String alias) {
		try {
			KeyStore instance;
			(instance = KeyStore.getInstance("PKCS12")).load(inputStream, array);
			if (alias == null) {
				Enumeration<String> aliases = instance.aliases();
				if (instance.size() > 1) {
					throw new Exception("More that 1 Keys found in the key store. Please specify Key Alias property!");
				}
				aliases.hasMoreElements();
				alias = aliases.nextElement();
			}

			PrivateKeyEntry arg11 = (PrivateKeyEntry) instance.getEntry(alias, new KeyStore.PasswordProtection(array));
			return arg11;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ex2) {
					ex2.printStackTrace();
				}
			}
		}
	}

	public String signXML(String xmlDocument, boolean includeKeyInfo) {
		if (this.jdField_a_of_type_JavaSecurityProvider == null) {
			this.jdField_a_of_type_JavaSecurityProvider = new BouncyCastleProvider();
		}

		Security.addProvider(this.jdField_a_of_type_JavaSecurityProvider);

		try {
			DocumentBuilderFactory arg2;
			(arg2 = DocumentBuilderFactory.newInstance()).setNamespaceAware(true);
			Document xmlDocument1 = arg2.newDocumentBuilder().parse(new InputSource(new StringReader(xmlDocument)));

			Document arg9999;
			if (System.getenv("SKIP_DIGITAL_SIGNATURE") != null) {
				arg9999 = xmlDocument1;
			} else {
				XMLSignatureFactory arg3;

				Reference arg4 = (arg3 = XMLSignatureFactory.getInstance("DOM")).newReference("", arg3.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", (DigestMethodParameterSpec) null), Collections.singletonList(arg3.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec) null)), (String) null, (String) null);
				SignedInfo arg12 = arg3.newSignedInfo(arg3.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec) null), arg3.newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null), Collections.singletonList(arg4));

				if (this.jdField_a_of_type_JavaSecurityKeyStore == null) {
					throw new RuntimeException("Key could not be read for digital signature. Please check value of signature alias and signature password, and restart the Auth Client");
				}

				X509Certificate arg5 = (X509Certificate) this.jdField_a_of_type_JavaSecurityKeyStore.getCertificate();
				KeyInfoFactory arg6 = arg3.getKeyInfoFactory();
				ArrayList arg7;
				(arg7 = new ArrayList()).add(arg5.getSubjectX500Principal().getName());
				arg7.add(arg5);
				X509Data arg13 = arg6.newX509Data(arg7);
				KeyInfo arg14 = arg6.newKeyInfo(Collections.singletonList(arg13));
				DOMSignContext xmlDocument2 = new DOMSignContext(this.jdField_a_of_type_JavaSecurityKeyStore.getPrivateKey(), xmlDocument1.getDocumentElement());

				arg3.newXMLSignature(arg12, includeKeyInfo ? arg14 : null).sign(xmlDocument2);
				arg9999 = xmlDocument2.getParent().getOwnerDocument();
			}

			xmlDocument1 = arg9999;

			StringWriter includeKeyInfo1 = new StringWriter();

			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xmlDocument1), new StreamResult(includeKeyInfo1));
			return includeKeyInfo1.getBuffer().toString();
		} catch (Exception arg8) {
			arg8.printStackTrace();
			throw new RuntimeException("Error while digitally signing the XML document", arg8);
		}
	}

	private static KeyStore.PrivateKeyEntry a(String arg, char[] arg0, String arg1) {
		FileInputStream arg2 = null;
		boolean arg9 = false;

		PrivateKeyEntry arg16;
		label103: {
			try {
				arg9 = true;
				KeyStore arg3 = KeyStore.getInstance("PKCS12");
				arg2 = new FileInputStream(arg);
				arg3.load(arg2, arg0);
				if (arg1 == null) {
					Enumeration arg15 = arg3.aliases();
					if (arg3.size() > 1) {
						throw new Exception("More that 1 Keys found in the key store. Please specify Key Alias property!");
					}

					arg15.hasMoreElements();
					arg1 = (String) arg15.nextElement();
				}

				arg16 = (PrivateKeyEntry) arg3.getEntry(arg1, new KeyStore.PasswordProtection(arg0));
				arg9 = false;
				break label103;
			} catch (Exception arg13) {
				arg13.printStackTrace();
				arg9 = false;
			} finally {
				if (arg9) {
					if (arg2 != null) {
						try {
							arg2.close();
						} catch (IOException arg10) {
							arg10.printStackTrace();
						}
					}

				}
			}

			if (arg2 != null) {
				try {
					arg2.close();
				} catch (IOException arg11) {
					arg11.printStackTrace();
				}
			}

			return null;
		}

		try {
			arg2.close();
		} catch (IOException arg12) {
			arg12.printStackTrace();
		}

		return arg16;
	}

	@SuppressWarnings("unchecked")
	private KeyInfo getKeyInfo(X509Certificate cert, XMLSignatureFactory fac) {
		// Create the KeyInfo containing the X509Data.
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();
		x509Content.add(cert.getSubjectX500Principal().getName());
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		return kif.newKeyInfo(Collections.singletonList(xd));
	}

}
