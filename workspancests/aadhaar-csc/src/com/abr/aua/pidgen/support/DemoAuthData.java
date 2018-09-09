package com.abr.aua.pidgen.support;

public class DemoAuthData {

	private String jdField_a_of_type_JavaLangString;
	private Pi jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pi = new Pi();
	private Pa jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pa = new Pa();
	private Pfa jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pfa = new Pfa();

	public String getLanguageCode() {
		return this.jdField_a_of_type_JavaLangString;
	}

	public void setLanguageCode(String languageCode) {
		this.jdField_a_of_type_JavaLangString = languageCode;
	}

	public Pi getPersonalIdentity() {
		return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pi;
	}

	public void setPersonalIdentity(Pi personalIdentity) {
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pi = personalIdentity;
	}

	public Pa getPersonalAddress() {
		return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pa;
	}

	public void setPersonalAddress(Pa personalAddress) {
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pa = personalAddress;
	}

	public Pfa getPersonalFullAddress() {
		return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pfa;
	}

	public void setPersonalFullAddress(Pfa personalFullAddress) {
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pfa = personalFullAddress;
	}

	public DemoAuthData() {
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pi.setMatchingStrategy(PiMatchingStrategy.NONE);
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pi.setDateOfBirthType(PiDateOfBirthType.NONE);
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pi.setGender(PiGender.NONE);
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pa.setMatchingStrategy(PaMatchingStrategy.NONE);
		this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$Pfa.setMatchingStrategy(PfaMatchingStrategy.NONE);
	}

	public class Pa {
		private boolean jdField_a_of_type_Boolean;
		private DemoAuthData.PaMatchingStrategy jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PaMatchingStrategy;
		private String jdField_a_of_type_JavaLangString;
		private String b;
		private String c;
		private String d;
		private String e;
		private String f;
		private String g;
		private String h;
		private String i;
		private String j;
		private String k;
		private String l;

		public boolean isUsePa() {
			return this.jdField_a_of_type_Boolean;
		}

		public void setUsePa(boolean usePa) {
			this.jdField_a_of_type_Boolean = usePa;
		}

		public DemoAuthData.PaMatchingStrategy getMatchingStrategy() {
			return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PaMatchingStrategy;
		}

		public void setMatchingStrategy(DemoAuthData.PaMatchingStrategy matchingStrategy) {
			this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PaMatchingStrategy = matchingStrategy;
		}

		public String getCareOf() {
			return this.jdField_a_of_type_JavaLangString;
		}

		public void setCareOf(String careOf) {
			this.jdField_a_of_type_JavaLangString = careOf;
		}

		public String getHouse() {
			return this.b;
		}

		public void setHouse(String house) {
			this.b = house;
		}

		public String getStreet() {
			return this.c;
		}

		public void setStreet(String street) {
			this.c = street;
		}

		public String getLandmark() {
			return this.d;
		}

		public void setLandmark(String landmark) {
			this.d = landmark;
		}

		public String getLocality() {
			return this.e;
		}

		public void setLocality(String locality) {
			this.e = locality;
		}

		public String getVillageTownCity() {
			return this.f;
		}

		public void setVillageTownCity(String villageTownCity) {
			this.f = villageTownCity;
		}

		public String getSubDistrict() {
			return this.g;
		}

		public void setSubDistrict(String subDistrict) {
			this.g = subDistrict;
		}

		public String getDistrict() {
			return this.h;
		}

		public void setDistrict(String district) {
			this.h = district;
		}

		public String getState() {
			return this.i;
		}

		public void setState(String state) {
			this.i = state;
		}

		public String getPincode() {
			return this.k;
		}

		public void setPincode(String pincode) {
			this.k = pincode;
		}

		public String getPostOffice() {
			return this.l;
		}

		public void setPostOffice(String postOffice) {
			this.l = postOffice;
		}

		public String getCountry() {
			return this.j;
		}

		public void setCountry(String country) {
			this.j = country;
		}
	}

	public static enum PaMatchingStrategy {
		E, NONE;
	}

	public class Pfa {
		private boolean jdField_a_of_type_Boolean;
		private DemoAuthData.PfaMatchingStrategy jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PfaMatchingStrategy;
		private int jdField_a_of_type_Int;
		private String jdField_a_of_type_JavaLangString;
		private int jdField_b_of_type_Int;
		private String jdField_b_of_type_JavaLangString;

		public boolean isUsePfa() {
			return this.jdField_a_of_type_Boolean;
		}

		public void setUsePfa(boolean usePfa) {
			this.jdField_a_of_type_Boolean = usePfa;
		}

		public DemoAuthData.PfaMatchingStrategy getMatchingStrategy() {
			return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PfaMatchingStrategy;
		}

		public void setMatchingStrategy(DemoAuthData.PfaMatchingStrategy matchingStrategy) {
			this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PfaMatchingStrategy = matchingStrategy;
		}

		public int getMatchingValue() {
			return this.jdField_a_of_type_Int;
		}

		public void setMatchingValue(int matchingValue) {
			this.jdField_a_of_type_Int = matchingValue;
		}

		public String getAddress() {
			return this.jdField_a_of_type_JavaLangString;
		}

		public void setAddress(String address) {
			this.jdField_a_of_type_JavaLangString = address;
		}

		public int getLocalAddressMatchingValue() {
			return this.jdField_b_of_type_Int;
		}

		public void setLocalAddressMatchingValue(int localAddressMatchingValue) {
			this.jdField_b_of_type_Int = localAddressMatchingValue;
		}

		public String getLocalAddress() {
			return this.jdField_b_of_type_JavaLangString;
		}

		public void setLocalAddress(String localAddress) {
			this.jdField_b_of_type_JavaLangString = localAddress;
		}
	}

	public static enum PfaMatchingStrategy {
		E, P, NONE;
	}

	public class Pi {
		private boolean jdField_a_of_type_Boolean;
		private DemoAuthData.PiMatchingStrategy jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiMatchingStrategy;
		private int jdField_a_of_type_Int;
		private String jdField_a_of_type_JavaLangString;
		private String jdField_b_of_type_JavaLangString;
		private int jdField_b_of_type_Int;
		private DemoAuthData.PiGender jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiGender;
		private String c;
		private DemoAuthData.PiDateOfBirthType jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiDateOfBirthType;
		private String d;
		private String e;
		private String f;

		public boolean isUsePi() {
			return this.jdField_a_of_type_Boolean;
		}

		public void setUsePi(boolean usePi) {
			this.jdField_a_of_type_Boolean = usePi;
		}

		public DemoAuthData.PiMatchingStrategy getMatchingStrategy() {
			return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiMatchingStrategy;
		}

		public void setMatchingStrategy(DemoAuthData.PiMatchingStrategy matchingStrategy) {
			this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiMatchingStrategy = matchingStrategy;
		}

		public int getMatchingValue() {
			return this.jdField_a_of_type_Int;
		}

		public void setMatchingValue(int matchingValue) {
			this.jdField_a_of_type_Int = matchingValue;
		}

		public String getName() {
			return this.jdField_a_of_type_JavaLangString;
		}

		public void setName(String name) {
			this.jdField_a_of_type_JavaLangString = name;
		}

		public String getLocalName() {
			return this.jdField_b_of_type_JavaLangString;
		}

		public void setLocalName(String localName) {
			this.jdField_b_of_type_JavaLangString = localName;
		}

		public int getLocalMatchingValue() {
			return this.jdField_b_of_type_Int;
		}

		public void setLocalMatchingValue(int localMatchingValue) {
			this.jdField_b_of_type_Int = localMatchingValue;
		}

		public DemoAuthData.PiGender getGender() {
			return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiGender;
		}

		public void setGender(DemoAuthData.PiGender gender) {
			this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiGender = gender;
		}

		public String getDateOfBirth() {
			return this.c;
		}

		public void setDateOfBirth(String dateOfBirth) {
			this.c = dateOfBirth;
		}

		public DemoAuthData.PiDateOfBirthType getDateOfBirthType() {
			return this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiDateOfBirthType;
		}

		public void setDateOfBirthType(DemoAuthData.PiDateOfBirthType dateOfBirthType) {
			this.jdField_a_of_type_ComEcsAuaPidgenSupportDemoAuthData$PiDateOfBirthType = dateOfBirthType;
		}

		public String getAge() {
			return this.d;
		}

		public void setAge(String age) {
			this.d = age;
		}

		public String getPhone() {
			return this.e;
		}

		public void setPhone(String phone) {
			this.e = phone;
		}

		public String geteMail() {
			return this.f;
		}

		public void seteMail(String eMail) {
			this.f = eMail;
		}
	}

	public static enum PiDateOfBirthType {
		V, D, A, NONE;
	}

	public static enum PiGender {
		M, F, T, NONE;
	}

	public static enum PiMatchingStrategy {
		E, P, NONE;
	}

}
