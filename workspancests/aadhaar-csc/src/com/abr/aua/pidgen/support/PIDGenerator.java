package com.abr.aua.pidgen.support;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.abr.aua.utils.ABRUtils;
import com.abr.aua.utils.XMLUtils;

import in.gov.uidai.authentication.uid_auth_request_data._1.Demo;
import in.gov.uidai.authentication.uid_auth_request_data._1.Gender;
import in.gov.uidai.authentication.uid_auth_request_data._1.MatchingStrategy;
import in.gov.uidai.authentication.uid_auth_request_data._1.Pa;
import in.gov.uidai.authentication.uid_auth_request_data._1.Pfa;
import in.gov.uidai.authentication.uid_auth_request_data._1.Pi;

public class PIDGenerator {

	static {
		Security.addProvider((Provider) new BouncyCastleProvider());
	}

	public EncPidData generateOTPPIDBlock(String version, String otp, String uidaiPublicKeyFile) throws Exception {
		return this.generateOTPPIDBlock(version, otp, new FileInputStream(new File(uidaiPublicKeyFile)));
	}

	public EncPidData generateOTPPIDBlock(String version, String otp, byte[] uidaiPublicKey) throws Exception {
		return this.generateOTPPIDBlock(version, otp, new ByteArrayInputStream(uidaiPublicKey));
	}

	public EncPidData generateOTPPIDBlock(String version, String otp, InputStream uidaiPublicKey) throws Exception {
		byte[] byteArray = ABRUtils.toByteArray(uidaiPublicKey);
		uidaiPublicKey.close();
		String a = a(new Date());
		StringBuffer sb;
		(sb = new StringBuffer()).append(String.format("<Pid ts=\"%s\" ver=\"%s\">", a, version));
		sb.append(String.format("<Pv  otp=\"%s\" />", otp));
		sb.append("</Pid>");
		byte[] bytes = (otp = sb.toString()).getBytes();
		byte[] a2 = a();
		byte[] a3 = a(new ByteArrayInputStream(byteArray), a2);
		byte[] a4;
		byte[] encPIDData = new byte[(a4 = a(version, a2, bytes, a)).length + a.length()];
		System.arraycopy(a.getBytes(), 0, encPIDData, 0, a.getBytes().length);
		System.arraycopy(a4, 0, encPIDData, a.getBytes().length, a4.length);
		byte[] a5 = a(version, a2, a(bytes), a);
		otp = a(new ByteArrayInputStream(byteArray));
		return new EncPidData("OTP", a3, encPIDData, "X", a5, otp, a);
	}

	public EncPidData generateDemographicPIDBlock(String version, DemoAuthData demoData, String uidaiPublicKeyFile) throws Exception {
		return this.generateDemographicPIDBlock(version, demoData, new FileInputStream(new File(uidaiPublicKeyFile)));
	}

	public EncPidData generateDemographicPIDBlock(String version, DemoAuthData demoData, byte[] uidaiPublicKey) throws Exception {
		return this.generateDemographicPIDBlock(version, demoData, new ByteArrayInputStream(uidaiPublicKey));
	}

	public EncPidData generateDemographicPIDBlock(String version, DemoAuthData demoData, InputStream uidaiPublicKey) throws Exception {
		byte[] byteArray = ABRUtils.toByteArray(uidaiPublicKey);
		uidaiPublicKey.close();
		String a = a(new Date());
		StringBuffer sb;
		(sb = new StringBuffer()).append(String.format("<Pid ts=\"%s\" ver=\"%s\">", a, version));
		Demo object;
		(object = new Demo()).setLang(demoData.getLanguageCode());
		if (demoData.getPersonalIdentity().isUsePi()) {
			Pi pi = new Pi();
			object.setPi(pi);
			if (demoData.getPersonalIdentity().getMatchingStrategy() == DemoAuthData.PiMatchingStrategy.E) {
				pi.setMs(MatchingStrategy.E);
			} else if (demoData.getPersonalIdentity().getMatchingStrategy() == DemoAuthData.PiMatchingStrategy.P) {
				pi.setMs(MatchingStrategy.P);
			}
			if (demoData.getPersonalIdentity().getMatchingValue() > 0) {
				pi.setMv(demoData.getPersonalIdentity().getMatchingValue());
			}
			if (demoData.getPersonalIdentity().getName() != null) {
				pi.setName(demoData.getPersonalIdentity().getName());
			}
			if (demoData.getPersonalIdentity().getLocalMatchingValue() > 0) {
				pi.setLmv(demoData.getPersonalIdentity().getLocalMatchingValue());
			}
			if (demoData.getPersonalIdentity().getLocalName() != null) {
				pi.setLname(demoData.getPersonalIdentity().getLocalName());
			}
			if (demoData.getPersonalIdentity().getAge() != null) {
				pi.setAge(Integer.parseInt(demoData.getPersonalIdentity().getAge()));
			}
			if (demoData.getPersonalIdentity().getGender() == DemoAuthData.PiGender.M) {
				pi.setGender(Gender.M);
			} else if (demoData.getPersonalIdentity().getGender() == DemoAuthData.PiGender.F) {
				pi.setGender(Gender.F);
			} else if (demoData.getPersonalIdentity().getGender() == DemoAuthData.PiGender.T) {
				pi.setGender(Gender.T);
			}
			if (demoData.getPersonalIdentity().getDateOfBirth() != null) {
				pi.setDob(demoData.getPersonalIdentity().getDateOfBirth());
			}
			if (demoData.getPersonalIdentity().getDateOfBirthType() != DemoAuthData.PiDateOfBirthType.NONE) {
				if (demoData.getPersonalIdentity().getDateOfBirthType() == DemoAuthData.PiDateOfBirthType.A) {
					pi.setDobt("A");
				} else if (demoData.getPersonalIdentity().getDateOfBirthType() == DemoAuthData.PiDateOfBirthType.D) {
					pi.setDobt("D");
				} else if (demoData.getPersonalIdentity().getDateOfBirthType() == DemoAuthData.PiDateOfBirthType.V) {
					pi.setDobt("V");
				}
			}
			if (demoData.getPersonalIdentity().getAge() != null) {
				pi.setAge(Integer.parseInt(demoData.getPersonalIdentity().getAge()));
			}
			if (demoData.getPersonalIdentity().getPhone() != null) {
				pi.setPhone(demoData.getPersonalIdentity().getPhone());
			}
			if (demoData.getPersonalIdentity().geteMail() != null) {
				pi.setEmail(demoData.getPersonalIdentity().geteMail());
			}
		}
		if (demoData.getPersonalAddress().isUsePa()) {
			Pa pa = new Pa();
			object.setPa(pa);
			if (demoData.getPersonalAddress().getMatchingStrategy() == DemoAuthData.PaMatchingStrategy.E) {
				pa.setMs(MatchingStrategy.E);
			}
			if (demoData.getPersonalAddress().getCareOf() != null) {
				pa.setCo(demoData.getPersonalAddress().getCareOf());
			}
			if (demoData.getPersonalAddress().getHouse() != null) {
				pa.setHouse(demoData.getPersonalAddress().getHouse());
			}
			if (demoData.getPersonalAddress().getStreet() != null) {
				pa.setStreet(demoData.getPersonalAddress().getStreet());
			}
			if (demoData.getPersonalAddress().getLandmark() != null) {
				pa.setLm(demoData.getPersonalAddress().getLandmark());
			}
			if (demoData.getPersonalAddress().getLocality() != null) {
				pa.setLoc(demoData.getPersonalAddress().getLocality());
			}
			if (demoData.getPersonalAddress().getVillageTownCity() != null) {
				pa.setVtc(demoData.getPersonalAddress().getVillageTownCity());
			}
			if (demoData.getPersonalAddress().getSubDistrict() != null) {
				pa.setSubdist(demoData.getPersonalAddress().getSubDistrict());
			}
			if (demoData.getPersonalAddress().getDistrict() != null) {
				pa.setDist(demoData.getPersonalAddress().getDistrict());
			}
			if (demoData.getPersonalAddress().getState() != null) {
				pa.setState(demoData.getPersonalAddress().getState());
			}
			if (demoData.getPersonalAddress().getPincode() != null) {
				pa.setPc(demoData.getPersonalAddress().getPincode());
			}
			if (demoData.getPersonalAddress().getPostOffice() != null) {
				pa.setPo(demoData.getPersonalAddress().getPostOffice());
			}
			if (demoData.getPersonalAddress().getCountry() != null) {
				pa.setCountry(demoData.getPersonalAddress().getCountry());
			}
		}
		if (demoData.getPersonalFullAddress().isUsePfa()) {
			Pfa pfa = new Pfa();
			object.setPfa(pfa);
			if (demoData.getPersonalFullAddress().getMatchingStrategy() == DemoAuthData.PfaMatchingStrategy.E) {
				pfa.setMs(MatchingStrategy.E);
			} else if (demoData.getPersonalFullAddress().getMatchingStrategy() == DemoAuthData.PfaMatchingStrategy.P) {
				pfa.setMs(MatchingStrategy.P);
			}
			if (demoData.getPersonalFullAddress().getMatchingValue() > 0) {
				pfa.setMv(demoData.getPersonalFullAddress().getMatchingValue());
			}
			if (demoData.getPersonalFullAddress().getAddress() != null) {
				pfa.setAv(demoData.getPersonalFullAddress().getAddress());
			}
			if (demoData.getPersonalFullAddress().getLocalAddressMatchingValue() > 0) {
				pfa.setLmv(demoData.getPersonalFullAddress().getLocalAddressMatchingValue());
			}
			if (demoData.getPersonalFullAddress().getLocalAddress() != null) {
				pfa.setLav(demoData.getPersonalFullAddress().getLocalAddress());
			}
		}
		sb.append(XMLUtils.serializeWithoutHeader(object));
		sb.append("</Pid>");

		System.out.println("Final PID BLOCk" + sb.toString());

		byte[] bytes = sb.toString().getBytes("UTF8");
		byte[] a2 = a();
		byte[] a3 = a(new ByteArrayInputStream(byteArray), a2);
		byte[] a4;
		byte[] encPIDData = new byte[(a4 = a(version, a2, bytes, a)).length + a.length()];
		System.arraycopy(a.getBytes(), 0, encPIDData, 0, a.getBytes().length);
		System.arraycopy(a4, 0, encPIDData, a.getBytes().length, a4.length);
		return new EncPidData("DEMO_AUTH", a3, encPIDData, "X", a(version, a2, a(bytes), a), a(new ByteArrayInputStream(byteArray)), a);
	}

	private static byte[] a(InputStream inputStream, byte[] array) throws IOException, GeneralSecurityException {
		PublicKey publicKey;
		if ((publicKey = ((X509Certificate) CertificateFactory.getInstance("X.509", "BC").generateCertificate(inputStream)).getPublicKey()) == null) {
			System.out.println("Public key is null");
		}
		Cipher instance;
		(instance = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC")).init(1, publicKey);
		return instance.doFinal(array);
	}

	private static byte[] a() throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyGenerator instance;
		(instance = KeyGenerator.getInstance("AES", "BC")).init(256);
		return instance.generateKey().getEncoded();
	}

	private static byte[] a(String s, byte[] array, byte[] array2, String s2) throws InvalidCipherTextException, NoSuchPaddingException, Exception {

		if (s.compareTo("2.0") == 0) {
			Cipher instance;
			(instance = Cipher.getInstance("AES/GCM/NoPadding", "BC")).init(1, new SecretKeySpec(array, "AES"), new IvParameterSpec(s2.substring(s2.length() - 12).getBytes()));
			instance.updateAAD(s2.substring(s2.length() - 16).getBytes());
			return instance.doFinal(array2);
		}
		throw new Exception("Invalid PID Version");
	}

	private static byte[] a(byte[] array) {
		byte[] digest = null;
		try {
			MessageDigest instance;
			(instance = MessageDigest.getInstance("SHA-256", "BC")).reset();
			digest = instance.digest(array);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return digest;
	}

	public static byte[] getKycWadh(String kycVersion, boolean fingerprint, boolean iris, boolean otp, boolean rc, boolean lr, boolean de, boolean pfr) throws Exception {
		String s = null;
		if (fingerprint && !iris && !otp) {
			fingerprint = Boolean.valueOf("F");
		} else if (!fingerprint && iris && !otp) {
			s = "I";
		} else if (!fingerprint && !iris && otp) {
			s = "O";
		} else if (fingerprint && iris && !otp) {
			s = "FI";
		} else if (fingerprint && !iris && otp) {
			s = "FO";
		} else if (!fingerprint && iris && otp) {
			s = "IO";
		} else {
			if (!fingerprint || !iris || !otp) {
				throw new Exception("Invalid value for Ra for generating Wadh");
			}
			s = "FIO";
		}
		if (!rc) {
			throw new Exception("Invalid Resident consent");
		}
		kycVersion = String.valueOf(kycVersion) + s + (rc ? "Y" : "N") + (lr ? "Y" : "N") + (de ? "Y" : "N") + (pfr ? "Y" : "N");
		MessageDigest instance;
		(instance = MessageDigest.getInstance("SHA-256", "BC")).reset();
		return instance.digest(kycVersion.getBytes());
	}

	private static String a(Date date) {
		return String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(date)) + "T" + new SimpleDateFormat("HH:mm:ss").format(date);
	}

	private static String a(InputStream inputStream) throws Exception {
		Date notAfter = ((X509Certificate) CertificateFactory.getInstance("X.509", "BC").generateCertificate(inputStream)).getNotAfter();
		SimpleDateFormat simpleDateFormat;
		(simpleDateFormat = new SimpleDateFormat("yyyyMMdd")).setTimeZone(TimeZone.getTimeZone("GMT"));
		return simpleDateFormat.format(notAfter);
	}

}
