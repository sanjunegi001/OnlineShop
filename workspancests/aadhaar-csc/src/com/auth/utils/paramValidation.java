package com.auth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class paramValidation {

	public boolean isAaadharValid(String aadhaar) {
		Pattern p = Pattern.compile("((^[1-9][0-9]{11})|(^[1-9][0-9]{15}))$");
		Matcher numberMatcher;
		boolean isAadhaar;
		numberMatcher = p.matcher(aadhaar);

		if (numberMatcher.matches()) {
			isAadhaar = true;
			return isAadhaar;
		} else {
			isAadhaar = false;
			return isAadhaar;
		}
	}

	public boolean isClientId(String clientid) {
		Pattern p = Pattern.compile("(^[a-zA-Z0-9]{4,25}$)$");
		Matcher numberMatcher;
		boolean isClient;
		numberMatcher = p.matcher(clientid);

		if (numberMatcher.matches()) {
			isClient = true;
			return isClient;
		} else {
			isClient = false;
			return isClient;
		}
	}

	public boolean isAuthType(String authtype) {
		boolean isauthType;
		if (authtype.trim().equalsIgnoreCase("1") || authtype.trim().equalsIgnoreCase("2")) {
			isauthType = true;
			return isauthType;
		} else {
			isauthType = false;
			return isauthType;
		}

	}

	public boolean isChannel(String channel) {

		boolean ischannel;
		if (channel.trim().contentEquals("1") || channel.trim().contentEquals("2") || channel.trim().contentEquals("0")) {
			ischannel = true;
			return ischannel;
		} else {
			ischannel = false;
			return ischannel;
		}

	}

	public boolean isDobTypeValid(String dob_type) {
		boolean isdobtypeValid;
		if (dob_type.equalsIgnoreCase("V") || dob_type.equalsIgnoreCase("D") || dob_type.equalsIgnoreCase("A")) {
			isdobtypeValid = true;
			return isdobtypeValid;
		} else {
			isdobtypeValid = false;
			return isdobtypeValid;
		}
	}

	public boolean isgenderValid(String gender) {
		boolean isGender;
		if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F") || gender.equalsIgnoreCase("T")) {

			isGender = true;
			return isGender;
		} else {
			isGender = false;
			return isGender;
		}
	}

	public boolean ismobileValid(String mobile) {
		boolean isMobile;
		Pattern pattern = Pattern.compile("^[0-9]{10}$");
		Matcher matcher = pattern.matcher(mobile);

		if (matcher.matches()) {
			isMobile = true;
			return isMobile;
		} else {
			isMobile = false;
			return isMobile;
		}
	}

	public boolean isemailValid(String Email) {
		boolean isEmail;
		Pattern epattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = epattern.matcher(Email);
		if (matcher.matches()) {
			isEmail = true;
			return isEmail;
		} else {
			isEmail = false;
			return isEmail;
		}
	}

	public Boolean isValidRequest(String aadhaar) {
		// TODO Auto-generated method stub

		System.out.println("aadhaar" + aadhaar);

		return null;
	}

}
