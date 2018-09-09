package com.abr.asa.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import com.abr.asa.utils.CSCResp;
import com.abr.aua.pidgen.support.DemoAuthData;
import com.abr.aua.pidgen.support.EncPidData;
import com.abr.aua.pidgen.support.PIDGenerator;
import com.abr.aua.utils.ABRUtils;
import com.abr.aua.utils.Base64;
import com.abr.aua.utils.XMLUtils;
import com.abr.exceptions.AuaServerException;
import com.abr.exceptions.PidServerException;
import com.abr.exceptions.XMLParsingException;
import com.abr.rd.xsd.RDPidData;

import in.gov.uidai.authentication.uid_auth_request._1.Auth;
import in.gov.uidai.authentication.uid_auth_request._1.RcFlag;
import in.gov.uidai.authentication.uid_auth_request._1.Skey;
import in.gov.uidai.authentication.uid_auth_request._1.UsesFlag;
import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;

public class AuthProcessor {

	private String jdField_a_of_type_JavaLangString;
	private RcType jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$RcType;
	private TidType jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TidType = TidType.None;
	private String jdField_b_of_type_JavaLangString;
	private String jdField_c_of_type_JavaLangString;
	private TxnPrefixType jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TxnPrefixType = TxnPrefixType.Authentication;
	private String d;
	private String e;
	private Uses jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Uses;
	private Meta jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Meta;
	private SKey jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$SKey;
	private Data jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Data;
	private byte[] jdField_a_of_type_ArrayOfByte;
	private byte[] jdField_b_of_type_ArrayOfByte;
	private byte[] jdField_c_of_type_ArrayOfByte;

	public AuthProcessor() {
	}

	public AuthProcessor(byte[] uidaiVerifyCertificate) {
		this(null, uidaiVerifyCertificate);
	}

	public AuthProcessor(byte[] uidaiPidEncryptionCertificate, byte[] uidaiVerifyCertificate) {
		this.jdField_b_of_type_ArrayOfByte = uidaiPidEncryptionCertificate;
		this.jdField_c_of_type_ArrayOfByte = uidaiVerifyCertificate;
	}

	public void prepareDemographicPidBlock(DemoAuthData demoData) throws Exception {

		if (this.jdField_b_of_type_ArrayOfByte == null) {
			throw new Exception("PID Block encryption certificate is not set");
		}

		setTid(TidType.None);
		Object localObject = ((PIDGenerator) (localObject = new PIDGenerator())).generateDemographicPIDBlock("2.0", demoData, this.jdField_b_of_type_ArrayOfByte);
		getUses().setBio(YesNo.No);
		getUses().setOtp(YesNo.No);
		getUses().setBt("");
		getUses().setPa(demoData.getPersonalAddress().isUsePa() ? YesNo.Yes : YesNo.No);
		getUses().setPfa(demoData.getPersonalFullAddress().isUsePfa() ? YesNo.Yes : YesNo.No);
		getUses().setPi(demoData.getPersonalIdentity().isUsePi() ? YesNo.Yes : YesNo.No);
		getUses().setPin(YesNo.No);
		getsKey().setCi(((EncPidData) localObject).getCertificateIdentifier());
		getsKey().setEncryptedSessionKey(((EncPidData) localObject).getEncryptedSessionKey());
		getData().setType(DataType.X);
		getData().setEncryptedPidBlock(((EncPidData) localObject).getEncPIDData());

		this.jdField_a_of_type_ArrayOfByte = ((EncPidData) localObject).getEncryptedHmac();
	}

	public static enum RcType {
		None, Y, N;
	}

	public class Data {

		private AuthProcessor.DataType jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$DataType;
		private byte[] jdField_a_of_type_ArrayOfByte;

		public Data() {
		}

		public AuthProcessor.DataType getType() {
			return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$DataType;
		}

		public void setType(AuthProcessor.DataType type) {
			this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$DataType = type;
		}

		public byte[] getEncryptedPidBlock() {
			return this.jdField_a_of_type_ArrayOfByte;
		}

		public void setEncryptedPidBlock(byte[] encryptedPidBlock) {
			this.jdField_a_of_type_ArrayOfByte = encryptedPidBlock;
		}

	}

	public Data getData() {
		if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Data == null) {
			this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Data = new Data();
		}
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Data;
	}

	public class SKey {
		private String jdField_a_of_type_JavaLangString;
		private byte[] jdField_a_of_type_ArrayOfByte;

		public SKey() {
		}

		public String getCi() {
			return this.jdField_a_of_type_JavaLangString;
		}

		public void setCi(String ci) {
			this.jdField_a_of_type_JavaLangString = ci;
		}

		public byte[] getEncryptedSessionKey() {
			return this.jdField_a_of_type_ArrayOfByte;
		}

		public void setEncryptedSessionKey(byte[] encryptedSessionKey) {
			this.jdField_a_of_type_ArrayOfByte = encryptedSessionKey;
		}
	}

	public SKey getsKey() {
		if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$SKey == null) {
			this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$SKey = new SKey();
		}
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$SKey;
	}

	public class Meta {

		private String jdField_a_of_type_JavaLangString;
		private String b;
		private String c;
		private String d;
		private String e;
		private String f;
		private byte[] jdField_a_of_type_ArrayOfByte;

		public Meta() {

		}

		public String getUdc() {
			return this.jdField_a_of_type_JavaLangString;
		}

		public void setUdc(String udc) {
			this.jdField_a_of_type_JavaLangString = udc;
		}

		public String getRdsId() {
			return this.b;
		}

		public void setRdsId(String rdsId) {
			this.b = rdsId;
		}

		public String getRdsVer() {
			return this.c;
		}

		public void setRdsVer(String rdsVer) {
			this.c = rdsVer;
		}

		public String getDpId() {
			return this.d;
		}

		public void setDpId(String dpId) {
			this.d = dpId;
		}

		public String getDc() {
			return this.e;
		}

		public void setDc(String dc) {
			this.e = dc;
		}

		public byte[] getMc() {
			return this.jdField_a_of_type_ArrayOfByte;
		}

		public void setMc(byte[] mc) {
			this.jdField_a_of_type_ArrayOfByte = mc;
		}

		public String getMi() {
			return this.f;
		}

		public void setMi(String mi) {
			this.f = mi;
		}
	}

	public Meta getMeta() {
		if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Meta == null) {
			this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Meta = new Meta();
		}
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Meta;
	}

	public Uses getUses() {
		if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Uses == null) {
			this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Uses = new Uses();
		}
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$Uses;
	}

	public class Uses {
		private AuthProcessor.YesNo jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$YesNo;
		private AuthProcessor.YesNo b;
		private AuthProcessor.YesNo c;
		private AuthProcessor.YesNo d;
		private String jdField_a_of_type_JavaLangString;
		private AuthProcessor.YesNo e;
		private AuthProcessor.YesNo f;

		public Uses() {
		}

		public AuthProcessor.YesNo getPi() {
			return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$YesNo;
		}

		public void setPi(AuthProcessor.YesNo pi) {
			this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$YesNo = pi;
		}

		public AuthProcessor.YesNo getPa() {
			return this.b;
		}

		public void setPa(AuthProcessor.YesNo pa) {
			this.b = pa;
		}

		public AuthProcessor.YesNo getPfa() {
			return this.c;
		}

		public void setPfa(AuthProcessor.YesNo pfa) {
			this.c = pfa;
		}

		public AuthProcessor.YesNo getBio() {
			return this.d;
		}

		public void setBio(AuthProcessor.YesNo bio) {
			this.d = bio;
		}

		public String getBt() {
			return this.jdField_a_of_type_JavaLangString;
		}

		public void setBt(String bt) {
			this.jdField_a_of_type_JavaLangString = bt;
		}

		public AuthProcessor.YesNo getPin() {
			return this.e;
		}

		public void setPin(AuthProcessor.YesNo pin) {
			this.e = pin;
		}

		public AuthProcessor.YesNo getOtp() {
			return this.f;
		}

		public void setOtp(AuthProcessor.YesNo otp) {
			this.f = otp;
		}
	}

	public static enum TidType {
		None, registered;
	}

	public static enum DataType {
		None, X, P;
	}

	public static enum YesNo {
		None, Yes, No;
	}

	public String getUid() {
		return this.jdField_a_of_type_JavaLangString;
	}

	public void setUid(String uid) {
		this.jdField_a_of_type_JavaLangString = uid;
	}

	public RcType getRc() {
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$RcType;
	}

	public void setRc(RcType rc) {
		this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$RcType = rc;
	}

	public TidType getTid() {
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TidType;
	}

	public void setTid(TidType tid) {
		this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TidType = tid;
	}

	public String getAc() {
		return this.jdField_b_of_type_JavaLangString;
	}

	public void setAc(String ac) {
		this.jdField_b_of_type_JavaLangString = ac;
	}

	public String getSa() {
		return this.jdField_c_of_type_JavaLangString;
	}

	public void setSa(String sa) {
		this.jdField_c_of_type_JavaLangString = sa;
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

	public TxnPrefixType getTxnPrefix() {
		return this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TxnPrefixType;
	}

	public void setTxnPrefix(TxnPrefixType txnPrefix) {
		this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TxnPrefixType = txnPrefix;
	}

	public static enum TxnPrefixType {
		None, Authentication, Kyc;
	}

	public String getSignedXml(byte[] signCertificate, String certificatePassword, String utransactionId) throws Exception {
		if (signCertificate == null)
			throw new Exception("Signing certificate cannot be null");
		if (certificatePassword == null)
			throw new Exception("Certificate password cannot be null");
		String str = getXml(utransactionId);

		return str;

		// return new DigitalSignerP12(new ByteArrayInputStream(signCertificate), certificatePassword.toCharArray()).signXML(str, true);

	}

	private String getXml(String utransactionId) throws Exception {

		if (ABRUtils.isNullOrEmpty(this.jdField_a_of_type_JavaLangString)) {
			throw new Exception("UID/VID cannot be null or empty");
		}

		if (!this.jdField_a_of_type_JavaLangString.trim().matches("((^[1-9][0-9]{11})|(^[1-9][0-9]{15}))$")) {
			throw new Exception("Invalid UID/VID. Length Error (must be 12/16 digits)");
		}

		if (!ABRUtils.isNumeric(this.jdField_a_of_type_JavaLangString.trim())) {
			throw new Exception("Invalid uid/vid. Invalid characters");
		}

		if (ABRUtils.isNullOrEmpty(this.jdField_b_of_type_JavaLangString)) {
			throw new Exception("Invalid ac (aua code). Aua code cannot be null or empty");
		}
		if ((this.jdField_b_of_type_JavaLangString.trim().length() != 10) && (this.jdField_b_of_type_JavaLangString.compareTo("public") != 0)) {
			throw new Exception("Invalid ac (aua code). Length Error (must be 10 digits or alpha numeric)");
		}
		if (ABRUtils.isNullOrEmpty(this.jdField_c_of_type_JavaLangString)) {
			throw new Exception("Invalid Sa (subaua code). SubAua code cannot be null or empty");
		}
		if (getUses().getPi() == YesNo.None) {
			throw new Exception("Uses->pi not set");
		}
		if (getUses().getPa() == YesNo.None) {
			throw new Exception("Uses->pa not set");
		}
		if (getUses().getPfa() == YesNo.None) {
			throw new Exception("Uses->pfa not set");
		}
		if (getUses().getBio() == YesNo.None) {
			throw new Exception("Uses->bio not set");
		}
		if (getUses().getOtp() == YesNo.None) {
			throw new Exception("Uses->otp not set");
		}
		if ((getUses().getBio() == YesNo.Yes) && (getUses().getBt() == null)) {
			throw new Exception("Uses->bt not set");
		} else {

			String arg6;
			int arg8;

			if (ABRUtils.isNullOrEmpty(this.d)) {
				String arg0;
				if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TxnPrefixType == TxnPrefixType.Authentication) {

					// System.out.println("Date1");
					// arg0 = this.jdField_b_of_type_JavaLangString;
					// int arg1 = (int) (Math.random() * 9000000.0D) + 1000000;
					// SimpleDateFormat arg2 = new SimpleDateFormat("yyyyMMddkkmm");

					System.out.println("utransactionId" + utransactionId);

					this.d = utransactionId;

					// this.d = arg2.format(new Date()).substring(2) + ":" + arg0 + ":" + arg1;

				} else if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TxnPrefixType == TxnPrefixType.Kyc) {

					arg6 = this.jdField_b_of_type_JavaLangString;
					arg0 = "UKC:";
					arg8 = (int) (Math.random() * 9000000.0D) + 1000000;
					SimpleDateFormat arg3 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
					this.d = arg0 + arg6 + ":" + arg3.format(new Date()) + ":" + arg8;
				}

			} else if (this.getUses().getOtp() == YesNo.Yes && this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TxnPrefixType == TxnPrefixType.Kyc) {
				this.d = "UKC:" + this.d;
			}

			if (ABRUtils.isNullOrEmpty(this.e)) {
				throw new Exception("License Lk cannot be null or empty");
			}

			else {
				if (getUses().getBio() == YesNo.Yes) {
					if (ABRUtils.isNullOrEmpty(getMeta().getRdsId()))
						throw new Exception("Meta->rdsId cannot be null or empty");
					if (ABRUtils.isNullOrEmpty(getMeta().getRdsVer()))
						throw new Exception("Meta->rdsVer cannot be null or empty");
					if (ABRUtils.isNullOrEmpty(getMeta().getDpId()))
						throw new Exception("Meta->dpId cannot be null or empty");
					if (ABRUtils.isNullOrEmpty(getMeta().getDc()))
						throw new Exception("Meta->dc cannot be null or empty");
					if (ABRUtils.isNullOrEmpty(getMeta().getMi()))
						throw new Exception("Meta->mi cannot be null or empty");
					if (getMeta().getMc() == null)
						throw new Exception("Meta->mc cannot be null or empty");
				}

				if (this.getUses().getPi() == YesNo.No && this.getUses().getPa() == YesNo.No && this.getUses().getPfa() == YesNo.No && this.getUses().getBio() == YesNo.No && this.getUses().getOtp() == YesNo.No) {
					throw new Exception("Invalid Uses Parameter values. Atlease one attribute should have value Y");
				} else if (ABRUtils.isNullOrEmpty(this.getsKey().getCi())) {
					throw new Exception("Skey->ci cannot be null or empty");
				} else if (this.getsKey().getEncryptedSessionKey() == null) {
					throw new Exception("Skey->encryptedSessionKey cannot be null or empty");
				} else if (this.getData().getType() == DataType.None) {
					throw new Exception("Data->type not set");
				} else if (this.getData().getEncryptedPidBlock() == null) {
					throw new Exception("Data->encryptedPidBlock not set");
				} else if (this.jdField_a_of_type_ArrayOfByte == null) {
					throw new Exception("HMac not set");
				} else {

					if (this.getUses().getBio() == YesNo.Yes) {

						String[] arg4;
						int arg9 = (arg4 = this.getUses().getBt().split(",")).length;

						for (arg8 = 0; arg8 < arg9; ++arg8) {
							if ((arg6 = arg4[arg8]).trim().compareToIgnoreCase("FMR") != 0 && arg6.trim().compareToIgnoreCase("FIR") != 0 && arg6.trim().compareToIgnoreCase("IIR") != 0 && arg6.trim().compareToIgnoreCase("FID") != 0) {
								throw new Exception("Invalid value in bt [" + arg6 + "]");
							}
						}
					}

					in.gov.uidai.authentication.uid_auth_request._1.Uses arg5;
					(arg5 = new in.gov.uidai.authentication.uid_auth_request._1.Uses()).setBio(this.getUses().getBio() == YesNo.Yes ? UsesFlag.Y : UsesFlag.N);
					arg5.setPi(this.getUses().getPi() == YesNo.Yes ? UsesFlag.Y : UsesFlag.N);
					arg5.setPa(this.getUses().getPa() == YesNo.Yes ? UsesFlag.Y : UsesFlag.N);
					arg5.setPfa(this.getUses().getPfa() == YesNo.Yes ? UsesFlag.Y : UsesFlag.N);
					arg5.setPin(this.getUses().getPin() == YesNo.Yes ? UsesFlag.Y : UsesFlag.N);
					arg5.setOtp(this.getUses().getOtp() == YesNo.Yes ? UsesFlag.Y : UsesFlag.N);
					arg5.setBt(this.getUses().getBt());
					// if (this.getUses().getBio() == YesNo.Yes) {
					// arg5.setBt(this.getUses().getBt());
					// }

					in.gov.uidai.authentication.common.types._1.Meta arg7;
					(arg7 = new in.gov.uidai.authentication.common.types._1.Meta()).setUdc(this.getMeta().getUdc());
					if (getUses().getBio() == YesNo.Yes) {

						arg7.setRdsId(this.getMeta().getRdsId());
						arg7.setRdsVer(getMeta().getRdsVer());
						arg7.setDpId(getMeta().getDpId());
						arg7.setDc(getMeta().getDc());
						arg7.setMi(getMeta().getMi());
						if (getMeta().getMc() == null) {
							arg7.setMc("");
						} else {
							arg7.setMc(Base64.encode(this.getMeta().getMc()));
						}
					} else {

						arg7.setRdsId("");
						arg7.setRdsVer("");
						arg7.setDpId("");
						arg7.setDc("");
						arg7.setMi("");
						arg7.setMc("");
					}

					Skey arg10;
					(arg10 = new Skey()).setCi(this.getsKey().getCi());
					arg10.setValue(this.getsKey().getEncryptedSessionKey());
					in.gov.uidai.authentication.uid_auth_request._1.Auth.Data arg11;
					(arg11 = new in.gov.uidai.authentication.uid_auth_request._1.Auth.Data()).setType(this.getData().getType() == DataType.P ? in.gov.uidai.authentication.uid_auth_request._1.DataType.P : in.gov.uidai.authentication.uid_auth_request._1.DataType.X);
					arg11.setValue(this.getData().getEncryptedPidBlock());
					Auth arg12;
					(arg12 = new Auth()).setUid(this.jdField_a_of_type_JavaLangString);
					arg12.setVer("2.5");
					arg12.setAc(this.jdField_b_of_type_JavaLangString);
					arg12.setSa(this.jdField_c_of_type_JavaLangString);
					arg12.setRc(RcFlag.Y);
					if (this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TidType != TidType.None) {
						arg12.setTid(this.jdField_a_of_type_ComEcsAsaProcessorAuthProcessor$TidType.name());
					} else {
						arg12.setTid("");
					}

					arg12.setTxn(this.d);
					arg12.setLk(this.e);
					arg12.setUses(arg5);
					arg12.setMeta(arg7);
					arg12.setSkey(arg10);
					arg12.setData(arg11);
					arg12.setHmac(this.jdField_a_of_type_ArrayOfByte);
					return XMLUtils.serialize(arg12);

				}

			}

		}
	}

	public byte[] getHmac() {
		return this.jdField_a_of_type_ArrayOfByte;
	}

	public void setHmac(byte[] hmac) {
		this.jdField_a_of_type_ArrayOfByte = hmac;
	}

	public void setRDRespone(String rdxml, String bt, boolean pi, boolean pa, boolean pfa, boolean otp, boolean pin, String udc) throws Exception {

		if (rdxml == null) {
			throw new Exception("RD response Xml is null");
		} else {
			RDPidData localRDPidData = null;

			try {
				localRDPidData = (RDPidData) XMLUtils.deserialize(RDPidData.class, rdxml);
			}

			catch (Exception localException1) {
				throw new PidServerException(localException1.getMessage());
			}

			setTid(TidType.registered);
			getUses().setBio(YesNo.Yes);
			getUses().setBt(bt);
			getUses().setOtp(otp ? YesNo.Yes : YesNo.No);
			getUses().setPa(pa ? YesNo.Yes : YesNo.No);
			getUses().setPfa(pfa ? YesNo.Yes : YesNo.No);
			getUses().setPi(pi ? YesNo.Yes : YesNo.No);
			getUses().setPin(pin ? YesNo.Yes : YesNo.No);
			getsKey().setCi(localRDPidData.getSkey().getCi());
			getsKey().setEncryptedSessionKey(localRDPidData.getSkey().getValue());
			getMeta().setDc(localRDPidData.getDeviceInfo().getDc());
			getMeta().setDpId(localRDPidData.getDeviceInfo().getDpId());
			getMeta().setMc(localRDPidData.getDeviceInfo().getMc());
			getMeta().setMi(localRDPidData.getDeviceInfo().getMi());
			getMeta().setRdsId(localRDPidData.getDeviceInfo().getRdsId());
			getMeta().setRdsVer(localRDPidData.getDeviceInfo().getRdsVer());
			setHmac(localRDPidData.getHmac());
			getData().setType(DataType.valueOf(localRDPidData.getData().getType().name()));
			getData().setEncryptedPidBlock(localRDPidData.getData().getValue());

		}

	}

	public AuthRes parse(String xml) throws AuaServerException, XMLParsingException, JAXBException, XMLStreamException {

		CSCResp cscResp = null;

		cscResp = XMLUtils.deserialize(CSCResp.class, xml);

		if (cscResp.getRes_code().contains("10")) {

			return aesDecrypt.base64Decoded(AuthRes.class, cscResp.getRes_data());

		} else {
			throw new AuaServerException(cscResp.getRes_code(), cscResp.getRes_msg());
		}

	}

	public void prepareOtpPIDBlock(String otp, String udc) throws Exception {
		if (this.jdField_b_of_type_ArrayOfByte == null)
			throw new Exception("PID Block encryption certificate is not set");
		if (otp.trim().length() != 6)
			throw new Exception("Invalid OTP Value. length Error (expected 6 digits)");
		if (!(ABRUtils.isNumeric(otp.trim())))
			throw new Exception("Invalid OTP Value. Invalid input (only numeric values are allowed)");
		setTid(TidType.None);
		PIDGenerator localPIDGenerator;
		Object localObject = (localPIDGenerator = new PIDGenerator()).generateOTPPIDBlock("2.0", otp, this.jdField_b_of_type_ArrayOfByte);
		getUses().setBio(YesNo.No);
		getUses().setOtp(YesNo.Yes);
		getUses().setPa(YesNo.No);
		getUses().setPfa(YesNo.No);
		getUses().setPi(YesNo.No);
		getUses().setPin(YesNo.No);
		// getMeta().setUdc(udc);
		getsKey().setCi(((EncPidData) localObject).getCertificateIdentifier());
		getsKey().setEncryptedSessionKey(((EncPidData) localObject).getEncryptedSessionKey());
		getData().setType(DataType.X);
		getData().setEncryptedPidBlock(((EncPidData) localObject).getEncPIDData());
		this.jdField_a_of_type_ArrayOfByte = ((EncPidData) localObject).getEncryptedHmac();
	}

}