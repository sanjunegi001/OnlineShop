package com.abr.aua.pidgen.support;

public class EncPidData {

	private byte[] jdField_a_of_type_ArrayOfByte;
	private byte[] jdField_b_of_type_ArrayOfByte;
	private byte[] jdField_c_of_type_ArrayOfByte;
	private String jdField_a_of_type_JavaLangString;
	private String jdField_b_of_type_JavaLangString;
	private String jdField_c_of_type_JavaLangString;
	private String d;

	public EncPidData() {
	}

	protected EncPidData(String pidType, byte[] encryptedSessionKey, byte[] encPIDData, String pidDataType, byte[] encryptedHmac, String certificateIdentifier, String pidTimestamp) {
		this.jdField_a_of_type_ArrayOfByte = encryptedSessionKey;
		this.jdField_b_of_type_ArrayOfByte = encPIDData;
		this.jdField_c_of_type_JavaLangString = pidDataType;
		this.jdField_c_of_type_ArrayOfByte = encryptedHmac;
		this.jdField_a_of_type_JavaLangString = certificateIdentifier;
		this.jdField_b_of_type_JavaLangString = pidTimestamp;
		this.d = pidType;
	}

	public byte[] getEncryptedSessionKey() {
		return this.jdField_a_of_type_ArrayOfByte;
	}

	public byte[] getEncPIDData() {
		return this.jdField_b_of_type_ArrayOfByte;
	}

	public byte[] getEncryptedHmac() {
		return this.jdField_c_of_type_ArrayOfByte;
	}

	public String getCertificateIdentifier() {
		return this.jdField_a_of_type_JavaLangString;
	}

	public String getPidTimestamp() {
		return this.jdField_b_of_type_JavaLangString;
	}

	public String getPidDataType() {
		return this.jdField_c_of_type_JavaLangString;
	}

	public String getPidType() {
		return this.d;
	}

}
