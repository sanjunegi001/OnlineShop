package com.abr.asa.processor;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.abr.asa.utils.CSCResp;
import com.abr.aua.utils.ABRUtils;
import com.abr.aua.utils.XMLUtils;
import com.abr.exceptions.AuaServerException;
import com.abr.exceptions.InvalidResponseException;
import com.abr.exceptions.UidaiSignatureVerificationFailedException;
import com.abr.exceptions.XMLParsingException;

import in.gov.uidai.authentication.otp._1.Opts;
import in.gov.uidai.authentication.otp._1.Otp;
import in.gov.uidai.authentication.otp._1.OtpRes;

public class OtpProcessor {
	private String jdField_a_of_type_JavaLangString;
	private TidType jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$TidType;
	private String b;
	private String c;
	private String d;
	private String e;
	private OtpType jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType;
	private ChannelType jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType;
	private byte[] jdField_a_of_type_ArrayOfByte;

	public OtpProcessor() {
	}

	public OtpProcessor(byte[] uidaiVerifyCertificate) {
		this.jdField_a_of_type_ArrayOfByte = uidaiVerifyCertificate;
	}

	public String getXml() throws Exception {

		if (!(ABRUtils.isNumeric(this.jdField_a_of_type_JavaLangString.trim())))
			throw new Exception("Invalid uid. Invalid characters in Aadhaar Number");

		if ((this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType == OtpType.MOBILE_NUMBER) && (this.jdField_a_of_type_JavaLangString.trim().length() != 10))
			throw new Exception("Invalid Mobile Number. Length Error (must be 10 digits)");
		if (ABRUtils.isNullOrEmpty(this.b))
			throw new Exception("Invalid ac (aua code). Aua code cannot be null or empty");
		if ((this.b.trim().length() != 10) && (this.b.compareTo("public") != 0))
			throw new Exception("Invalid ac (aua code). Length Error (must be 10 digits or alpha numeric)");
		if (ABRUtils.isNullOrEmpty(this.c))
			throw new Exception("Invalid Sa (subaua code). SubAua code cannot be null or empty");
		if (ABRUtils.isNullOrEmpty(this.d))
			this.d = a(this.b);
		if (ABRUtils.isNullOrEmpty(this.e))
			throw new Exception("AUA License Key not set");
		Otp localOtp;
		(localOtp = new Otp()).setUid(this.jdField_a_of_type_JavaLangString.trim());
		localOtp.setVer("2.5");
		localOtp.setAc(this.b);
		localOtp.setSa(this.c);
		localOtp.setLk(this.e);
		localOtp.setTid((this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$TidType == TidType.PUBLIC) ? "public" : null);
		if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType == OtpType.AADHAAR_NUMBER)
			localOtp.setType("A");
		else if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType == OtpType.MOBILE_NUMBER)
			localOtp.setType("M");
		else if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType == OtpType.VIRTUAL_NUMBER)
			localOtp.setType("V");

		localOtp.setTxn(a(this.c));
		Object localObject = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		localOtp.setTs(((SimpleDateFormat) localObject).format(new Date()));
		localObject = new Opts();
		if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType != ChannelType.NONE) {
			if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType == ChannelType.SMS_AND_EMAIL)
				((Opts) localObject).setCh("00");
			else if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType == ChannelType.SMS_ONLY)
				((Opts) localObject).setCh("01");
			else if (this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType == ChannelType.EMAIL_ONLY)
				((Opts) localObject).setCh("02");
			localOtp.setOpts((Opts) localObject);
		}
		return ((String) XMLUtils.serialize(localOtp));
	}

	public String getSignedXml(byte[] signCertificate, String certificatePassword) throws Exception {
		if (signCertificate == null)
			throw new Exception("Signing certificate cannot be null");
		if (certificatePassword == null)
			throw new Exception("Certificate password cannot be null");
		return getXml();
		// return (signCertificate = new DigitalSignerP12(new ByteArrayInputStream(signCertificate), certificatePassword.toCharArray())).signXML(str, true);
	}

	public String getUid() {
		return this.jdField_a_of_type_JavaLangString;
	}

	public void setUid(String uid) {
		this.jdField_a_of_type_JavaLangString = uid;
	}

	public TidType getTid() {
		return this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$TidType;
	}

	public void setTid(TidType tid) {
		this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$TidType = tid;
	}

	public String getAc() {
		return this.b;
	}

	public void setAc(String ac) {
		this.b = ac;
	}

	public String getSa() {
		return this.c;
	}

	public void setSa(String sa) {
		this.c = sa;
	}

	public String getTxn() {
		return this.d;
	}

	public void setTxn(String txn) {
		this.d = txn;
	}

	public String getLk() {
		return this.e;
	}

	public void setLk(String lk) {
		this.e = lk;
	}

	public OtpType getType() {
		return this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType;
	}

	public void setType(OtpType type) {
		this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$OtpType = type;
	}

	private static String a(String paramString) {

		int i = (int) (Math.random() * 9000000.0D) + 1000000;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		int arg1 = (int) (Math.random() * 9000000.0D) + 1000000;
		SimpleDateFormat arg2 = new SimpleDateFormat("yyyyMMddkkmm");

		return (arg2.format(new Date()).substring(2) + ":" + paramString + ":" + arg1);
	}

	public static enum ChannelType {
		NONE, SMS_AND_EMAIL, SMS_ONLY, EMAIL_ONLY;
	}

	public ChannelType getCh() {
		return this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType;
	}

	public void setCh(ChannelType ch) {
		this.jdField_a_of_type_ComEcsAsaProcessorOtpProcessor$ChannelType = ch;
	}

	public static enum OtpType {
		NONE, MOBILE_NUMBER, AADHAAR_NUMBER, VIRTUAL_NUMBER;
	}

	public static enum TidType {
		NONE, PUBLIC;
	}

	public class MobileEmail {
		private String a;
		private String b;

		public MobileEmail(String email, String mobile) {
			this.a = email;
			this.b = mobile;
		}

		public String getMobileNumber() {
			return this.a;
		}

		public String getEmail() {
			return this.b;
		}
	}

	public MobileEmail getMaskedMobileEmail(OtpRes otpres) throws Exception {

		if (!otpres.getRet().toString().equalsIgnoreCase("Y")) {
			throw new Exception("Cannot get Masked Mobile and Email address of failed OTP request");
		}
		if (otpres.getInfo() == null) {
			throw new Exception("Cannot get Masked Mobile and Email address - Info attribute is null");
		}

		String[] items = otpres.getInfo().replaceAll("01", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
		List<String> ilist = Arrays.asList(items);

		String str1 = "";
		String str = ilist.get(6);

		if (ilist.get(7).endsWith("}")) {
			str1 = ilist.get(7).substring(0, ilist.get(7).toString().length() - 1);
		} else {
			str1 = ilist.get(7);
		}
		if (str1.compareTo("NA") == 0) {
			str1 = null;
		}
		if (str.compareTo("NA") == 0) {
			str = null;
		}

		return new MobileEmail(str, str1);

	}

	public OtpRes parse(String xml) throws XMLParsingException, InvalidResponseException, UidaiSignatureVerificationFailedException, Exception {

		CSCResp cscResp = XMLUtils.deserialize(CSCResp.class, xml);

		if (cscResp.getRes_code().contains("10")) {

			return aesDecrypt.base64Decoded(OtpRes.class, cscResp.getRes_data());

		} else {
			throw new AuaServerException(cscResp.getRes_code(), cscResp.getRes_msg());
		}

	}

}
