package com.auth.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.auth.bean.Verification;
import com.auth.dao.VerificationDAO;
import com.auth.utils.PREAUAProperties;

import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;

@Service
@Configurable
public class BioService {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Autowired
	private VerificationDAO verificationDAO;

	public void saveBio(AuthRes authres, String request_time, String response_time, String flocation, String orgip, String fcity, String fpostalcode, String subAuaCode, String username, Map savemap, String utransactionId) throws ParseException {

		PREAUAProperties.load();

		String[] items = authres.getInfo().replaceAll("04", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
		List<String> infoData = Arrays.asList(items);

		Verification veri = new Verification();
		veri.setAPI_NAME("2.5");
		veri.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		veri.setSUB_AUA_CODE(subAuaCode);
		veri.setAUTH_TYPE("BIOAUTH");

		System.out.println("Reference ID" + authres.getCode());

		if (StringUtils.isNotEmpty(authres.getErr())) {
			veri.setMESSAGE("Authentication Failure");
			veri.setSTATUS(0);
		} else {
			veri.setMESSAGE("Authentication Success");
			veri.setSTATUS(1);

		}
		veri.setUID(infoData.get(0));
		veri.setUID_TYPE(infoData.get(1));
		veri.setERROR_CODE(authres.getErr());
		veri.setTRANSACTION_ID(utransactionId);
		veri.setSERVER_RESPONSE_ON(authres.getTs());
		veri.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		veri.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		veri.setCOUNTRY(flocation);
		veri.setIPADDRESS(orgip);
		veri.setCITY(fcity);
		veri.setPINCODE(Integer.parseInt(fpostalcode.trim()));
		veri.setREFERENCE_NUMBER(authres.getCode());
		veri.setREQUESTED_BY(username.toString());
		veri.setCONSENT(1);
		veri.setENV_TYPE("PROD");
		veri.setASA_NAME("CSC");

		if (savemap.containsKey("clientrefid")) {

			veri.setCLIENT_REF_ID(savemap.get("clientrefid").toString());
		}

		verificationDAO.save(veri);

	}

	public void saveExceptionBIO(String errorCode, String errorMessage, String request_time, String response_time, String flocation, String orgip, String fcity, String fpostalcode, String subAuaCode, String username, Map savemap, String utransactionId) throws ParseException {

		Verification veri = new Verification();

		veri.setAPI_NAME("2.5");
		veri.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		veri.setSUB_AUA_CODE(subAuaCode);
		veri.setAUTH_TYPE("BIOAUTH");
		veri.setUID("");
		veri.setTRANSACTION_ID(utransactionId);
		veri.setERROR_CODE(errorCode);
		veri.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		veri.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		veri.setCOUNTRY(flocation);
		veri.setIPADDRESS(orgip);
		veri.setCITY(fcity);
		veri.setPINCODE(Integer.parseInt(fpostalcode.trim()));
		veri.setMESSAGE("Authentication Failure");
		veri.setSTATUS_DESCRIPTION(errorMessage);
		veri.setSTATUS(0);
		veri.setREQUESTED_BY(username.toString());
		veri.setCONSENT(1);
		veri.setENV_TYPE("PROD");
		veri.setASA_NAME("CSC");

		if (savemap.containsKey("clientrefid")) {

			veri.setCLIENT_REF_ID(savemap.get("clientrefid").toString());
		}

		verificationDAO.save(veri);
	}

}
