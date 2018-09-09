package com.auth.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ab_otp_generation")
public class otpGeneration {

	@Id
	@SequenceGenerator(name = "seq_verification", sequenceName = "seq_verification")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_verification")

	private int ID;

	@Column
	private String TRANSACTION_ID;
	@Column
	private String REFERENCE_ID;
	@Column
	private String STATUS;
	@Column
	private String MESSAGE;
	@Column
	private String OTP_RESPONSE_CODE;
	@Column
	private String UID_TYPE;
	@Column
	private int OTP_AUTH_STATUS;
	@Column
	private String ERROR_CODE;
	@Column
	private String REQUESTED_BY;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date REQUEST_ON;

	/** The response on. */

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date RESPONSE_ON;
	@Column
	private String AUA_CODE;
	@Column
	private String SUB_AUA_CODE;
	@Column
	private String ENV_TYPE;
	@Column
	private String ASA_NAME;

	/**
	 * Instantiates a new deviceDetails.
	 */
	public otpGeneration() {
	}

	public otpGeneration(int ID, String TRANSACTION_ID, String UNIQUE_ID, String MESSAGE, int UID, String ERRORCODE, int OTP_STATUS, String REQUEST_BY, Date REQUEST_ON, Date RESPONSE_ON, String AUA_CODE, String SUB_AUA_CODE, String ASA_NAME, String ENV_TYPE, String CLIENT_REF_ID) {

		super();
		this.ID = ID;
		this.TRANSACTION_ID = TRANSACTION_ID;
		this.REFERENCE_ID = REFERENCE_ID;
		this.MESSAGE = MESSAGE;
		this.ERROR_CODE = ERROR_CODE;
		this.OTP_AUTH_STATUS = OTP_AUTH_STATUS;
		this.REQUESTED_BY = REQUESTED_BY;
		this.REQUEST_ON = REQUEST_ON;
		this.RESPONSE_ON = RESPONSE_ON;
		this.AUA_CODE = AUA_CODE;
		this.SUB_AUA_CODE = SUB_AUA_CODE;
		this.ASA_NAME = ASA_NAME;
		this.ENV_TYPE = ENV_TYPE;

	}

	public String getTRANSACTION_ID() {
		return TRANSACTION_ID;
	}

	public void setTRANSACTION_ID(String tRANSACTION_ID) {
		TRANSACTION_ID = tRANSACTION_ID;
	}

	public String getREFERENCE_ID() {
		return REFERENCE_ID;
	}

	public void setREFERENCE_ID(String rEFERENCE_ID) {
		REFERENCE_ID = rEFERENCE_ID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	public String getOTP_RESPONSE_CODE() {
		return OTP_RESPONSE_CODE;
	}

	public void setOTP_RESPONSE_CODE(String oTP_RESPONSE_CODE) {
		OTP_RESPONSE_CODE = oTP_RESPONSE_CODE;
	}

	public String getERRORCODE() {
		return ERROR_CODE;
	}

	public void setERRORCODE(String eRRORCODE) {
		ERROR_CODE = eRRORCODE;
	}

	public String getREQUEST_BY() {
		return REQUESTED_BY;
	}

	public void setREQUEST_BY(String rEQUEST_BY) {
		REQUESTED_BY = rEQUEST_BY;
	}

	public Date getREQUEST_ON() {
		return REQUEST_ON;
	}

	public void setREQUEST_ON(Date rEQUEST_ON) {
		REQUEST_ON = rEQUEST_ON;
	}

	public Date getRESPONSE_ON() {
		return RESPONSE_ON;
	}

	public void setRESPONSE_ON(Date rESPONSE_ON) {
		RESPONSE_ON = rESPONSE_ON;
	}

	public String getUID_TYPE() {
		return UID_TYPE;
	}

	public void setUID_TYPE(String uID_TYPE) {
		UID_TYPE = uID_TYPE;
	}

	public int getOTP_STATUS() {
		return OTP_AUTH_STATUS;
	}

	public void setOTP_STATUS(int oTP_STATUS) {
		OTP_AUTH_STATUS = oTP_STATUS;
	}

	public String getAUA_CODE() {
		return AUA_CODE;
	}

	public void setAUA_CODE(String aUA_CODE) {
		AUA_CODE = aUA_CODE;
	}

	public String getSUB_AUA_CODE() {
		return SUB_AUA_CODE;
	}

	public void setSUB_AUA_CODE(String sUB_AUA_CODE) {
		SUB_AUA_CODE = sUB_AUA_CODE;
	}

	public String getENV_TYPE() {
		return ENV_TYPE;
	}

	public void setENV_TYPE(String eNV_TYPE) {
		ENV_TYPE = eNV_TYPE;
	}

	public String getASA_NAME() {
		return ASA_NAME;
	}

	public void setASA_NAME(String aSA_NAME) {
		ASA_NAME = aSA_NAME;
	}

}
