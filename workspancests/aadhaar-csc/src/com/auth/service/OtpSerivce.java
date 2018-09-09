package com.auth.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.auth.bean.Verification;
import com.auth.bean.otpGeneration;
import com.auth.dao.OtpGenDAO;
import com.auth.dao.VerificationDAO;
import com.auth.utils.PREAUAProperties;

import in.gov.uidai.authentication.otp._1.OtpRes;
import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;

@Service
@Configurable
public class OtpSerivce {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Autowired
	private OtpGenDAO otpgenDAO;

	@Autowired
	private VerificationDAO verificationDAO;

	public void saveOtpGen(OtpRes ores, String msg, String utransactionId, String refid, String request_time, String response_time, String subAuaCode, String username) throws ParseException {

		String[] items = ores.getInfo().replaceAll("01", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
		List<String> infoData = Arrays.asList(items);

		otpGeneration ogen = new otpGeneration();

		if (StringUtils.isNotEmpty(ores.getErr())) {
			ogen.setSTATUS("1");
			ogen.setOTP_STATUS(2);
			ogen.setERRORCODE(ores.getErr());
		} else {
			ogen.setSTATUS("1");
			ogen.setOTP_STATUS(0);
			ogen.setERRORCODE("");
		}
		ogen.setOTP_RESPONSE_CODE(ores.getCode());

		ogen.setUID_TYPE(infoData.get(0));
		ogen.setTRANSACTION_ID(ores.getTxn());

		ogen.setREFERENCE_ID(refid);
		ogen.setMESSAGE(msg);
		ogen.setREQUEST_BY(username);
		ogen.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		ogen.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		ogen.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		ogen.setSUB_AUA_CODE(subAuaCode);
		ogen.setASA_NAME("CSC");
		ogen.setENV_TYPE("PROD");

		int ii = otpgenDAO.save(ogen);

	}

	public void saveOtpGenException(String code, String msg, String utransactionId, String refid, String request_time, String response_time, String subAuaCode, String username) throws ParseException {
		otpGeneration ogen = new otpGeneration();
		ogen.setOTP_RESPONSE_CODE("");
		ogen.setTRANSACTION_ID(utransactionId);
		ogen.setREFERENCE_ID(refid);
		ogen.setSTATUS("1");
		ogen.setMESSAGE(msg);
		ogen.setREQUEST_BY(username);
		ogen.setOTP_STATUS(1);
		ogen.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		ogen.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		ogen.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		ogen.setSUB_AUA_CODE(subAuaCode);
		ogen.setERRORCODE(code);
		ogen.setASA_NAME("CSC");
		ogen.setENV_TYPE("PROD");
		int ii = otpgenDAO.save(ogen);
	}

	public void errorOtpGen(String username, String errmsg, String errocode, String request_time, String response_time) throws ParseException {

		otpGeneration ogen = new otpGeneration();
		ogen.setOTP_RESPONSE_CODE("");
		ogen.setTRANSACTION_ID("");
		ogen.setREFERENCE_ID("");
		ogen.setSTATUS("0");
		ogen.setMESSAGE(errmsg);
		ogen.setREQUEST_BY(username);
		ogen.setOTP_STATUS(1);
		ogen.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		ogen.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		ogen.setERRORCODE(errocode);
		ogen.setASA_NAME("CSC");
		ogen.setENV_TYPE("PROD");
		int ii = otpgenDAO.save(ogen);
	}

	public void saveOtpVer(AuthRes authres, String refid, String request_time, String response_time, String flocation, String orgip, String fcity, String fpostalcode, String subAuaCode, String username, String erMsg, String utransactionId) throws ParseException {

		String[] items = authres.getInfo().replaceAll("04", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
		List<String> infoData = Arrays.asList(items);

		Verification veri = new Verification();
		veri.setAPI_NAME("2.5");
		veri.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		veri.setSUB_AUA_CODE(subAuaCode);
		veri.setAUTH_TYPE("OTPAUTH");

		if (StringUtils.isNotEmpty(authres.getErr())) {
			veri.setMESSAGE("Authentication Failure");
			veri.setSTATUS(0);
		} else {
			veri.setMESSAGE("Authentication Success");
			veri.setSTATUS(1);
		}

		veri.setUID(infoData.get(0));
		veri.setUID_TYPE(infoData.get(1));
		veri.setREFERENCE_NUMBER(authres.getCode());
		veri.setCLIENT_REF_ID(refid);
		veri.setERROR_CODE(authres.getErr());
		veri.setERROR_MSG(erMsg);
		veri.setTRANSACTION_ID(authres.getTxn());
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
		verificationDAO.save(veri);
	}

	public void saveExceptionGenOtp(String errorCode, String errorMessage, String aadharcardnumber, String request_time, String response_time, String flocation, String orgip, String fcity, String fpostalcode, String subAuaCode, String username) throws ParseException {

		otpGeneration ogen = new otpGeneration();
		ogen.setSTATUS("0");
		ogen.setOTP_STATUS(2);
		ogen.setERRORCODE(errorCode);
		ogen.setOTP_RESPONSE_CODE("");
		ogen.setTRANSACTION_ID("");
		ogen.setREFERENCE_ID("");
		ogen.setMESSAGE(errorMessage);
		ogen.setREQUEST_BY(username);
		ogen.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		ogen.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		ogen.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		ogen.setSUB_AUA_CODE(subAuaCode);
		ogen.setASA_NAME("CSC");
		ogen.setENV_TYPE("PROD");

		int ii = otpgenDAO.save(ogen);

	}

	public void saveExceptionOtp(String errorCode, String errorMessage, String crefid, String request_time, String response_time, String flocation, String orgip, String fcity, String fpostalcode, String subAuaCode, String username, String utransactionId) throws ParseException {

		Verification veri = new Verification();

		veri.setAPI_NAME("2.5");
		veri.setAUA_CODE(PREAUAProperties.getUidai_aua_code());
		veri.setSUB_AUA_CODE(subAuaCode);
		veri.setAUTH_TYPE("OTPAUTH");
		veri.setCLIENT_REF_ID(crefid);
		veri.setERROR_CODE(errorCode);
		veri.setREQUEST_ON(new Timestamp(dateFormat.parse(request_time).getTime()));
		veri.setRESPONSE_ON(new Timestamp(dateFormat.parse(response_time).getTime()));
		veri.setCOUNTRY(flocation);
		veri.setIPADDRESS(orgip);
		veri.setCITY(fcity);
		veri.setPINCODE(Integer.parseInt(fpostalcode.trim()));
		veri.setMESSAGE("Authentication Failure");
		veri.setERROR_MSG(errorMessage);
		veri.setSTATUS(0);
		veri.setREQUESTED_BY(username.toString());
		veri.setCONSENT(1);
		veri.setENV_TYPE("PROD");
		veri.setASA_NAME("CSC");
		veri.setTRANSACTION_ID(utransactionId);

		verificationDAO.save(veri);
	}

}
