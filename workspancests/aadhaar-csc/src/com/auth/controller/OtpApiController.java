package com.auth.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abr.asa.processor.AuthProcessor;
import com.abr.asa.processor.AuthProcessor.RcType;
import com.abr.asa.processor.AuthProcessor.TidType;
import com.abr.asa.processor.HttpConnector;
import com.abr.asa.processor.OtpProcessor;
import com.abr.asa.processor.OtpProcessor.ChannelType;
import com.abr.asa.processor.OtpProcessor.MobileEmail;
import com.abr.asa.processor.OtpProcessor.OtpType;
import com.abr.asa.processor.aesEncrypt;
import com.abr.asa.request.data.AsaRequest;
import com.abr.aua.utils.XMLUtils;
import com.abr.exceptions.AuaServerException;
import com.abr.exceptions.UidaiSignatureVerificationFailedException;
import com.abr.exceptions.XMLParsingException;
import com.auth.bean.otpGeneration;
import com.auth.bean.subAua;
import com.auth.bean.tokenDetails;
import com.auth.dao.OtpGenDAO;
import com.auth.dao.SubAuaDAO;
import com.auth.dao.TokenDetailsDAO;
import com.auth.service.OtpSerivce;
import com.auth.utils.AUAUtilities;
import com.auth.utils.Log;
import com.auth.utils.PREAUAProperties;
import com.auth.utils.paramValidation;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import in.gov.uidai.authentication.otp._1.OtpRes;
import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;

@Controller
public class OtpApiController {

	@Autowired
	private SubAuaDAO subauadao;

	@Autowired
	private OtpSerivce otpservice;

	@Autowired
	private OtpGenDAO otpgenDao;

	@Autowired
	private TokenDetailsDAO tokendetailsdao;

	@RequestMapping(value = "/v2/otpapi", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView otpauth(@RequestBody String jsondata, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) throws Exception {

		// Global Variables
		String requestXml = "", msg_results = "", request_time_csc = "", oResponseXml = "", fXml = "", odata = "", token = "", client_id = "", mhashData = "", hasingData = "", oRequestXml = "", orgip = "", geofile = "", flocation = "", fpostalcode = "", fcity = "", isClientref = "", responseXml = "", asaData = "", hashData = "", encryptedhashData = "", auth_data = "", requesttime, responsetime = "", aadharcardnumber = "", verifyby = "", subAuaCode = "", request_time = "", asaRequestXml = "";

		token = "Y35tF745BmKGZJ5K6HyN3233REc4i97K";
		client_id = "AUA-ABG";
		PREAUAProperties.load();
		Log.aua.info("CONSENT : CONSENT TAKEN BY USER!");
		Properties propertiesquery = new Properties();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date reqdate = new Date();
		requesttime = dateFormat.format(reqdate);

		Map<String, String> mapheader = new HashMap<String, String>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			mapheader.put(key, value);

		}

		if (request.getMethod().toUpperCase().contains("POST")) {
			if (mapheader.get("content-type").trim().equalsIgnoreCase("application/json")) {

				/** Request parameter checked */
				ClassLoader classloadererrorquery = Thread.currentThread().getContextClassLoader();
				propertiesquery.load(new FileInputStream(new File(classloadererrorquery.getResource("parameter.properties").getFile())));
				ClassLoader classLoader = getClass().getClassLoader();
				Properties properties = new Properties();

				ClassLoader classloadererror = Thread.currentThread().getContextClassLoader();
				properties.load(new FileInputStream(new File(classloadererror.getResource("aadhaarErrorCode.properties").getFile())));

				orgip = AUAUtilities.getClientIpAddr(request);
				/** Ipaddress captured **/
				geofile = PREAUAProperties.getGeofile();

				LookupService lookUp = new LookupService(PREAUAProperties.getGeofile(), LookupService.GEOIP_MEMORY_CACHE);

				try {
					lookUp = new LookupService(PREAUAProperties.getGeofile(), LookupService.GEOIP_MEMORY_CACHE);

					Location location = lookUp.getLocation(orgip);

					if (location != null) {
						flocation = location.countryName;
						fpostalcode = location.postalCode;
						fcity = location.city;
					} else {
						flocation = "India";
						fpostalcode = "122015";
						fcity = "Gurgaon";
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					Log.aua.info("Error Message::" + e1);
					e1.printStackTrace();

				}

				String utransactionId = "";
				int arg1 = (int) (Math.random() * 9000000.0D) + 1000000;
				SimpleDateFormat arg2 = new SimpleDateFormat("yyyyMMddkkmm");

				utransactionId = arg2.format(new Date()).substring(2) + ":" + PREAUAProperties.getUidai_aua_code() + ":" + arg1;

				try {

					if (StringUtils.isNotEmpty(mapheader.get("username"))) {

						boolean isDemoValidUser = subauadao.isValidClient(mapheader.get("username"));

						if (isDemoValidUser == false) {
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							Log.aua.info("User:::" + mapheader.get("username") + "::Invalid user");
							JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A210").add("Message", "You are not authorized to make such request. Please contact system administrator.");

							JsonObject dataJsonObject = value2.build();
							model.addAttribute("model", dataJsonObject);
							return new ModelAndView("otpauthapi");

						} else {

							subAua subauaDetails = subauadao.getSubAUA(mapheader.get("username"));
							tokenDetails clientToken = tokendetailsdao.getOneById(mapheader.get("username"));

							if (subauaDetails == null) {
								response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
								Log.aua.info("User:::" + mapheader.get("subauacode") + "::Invalid SubAUA");
								JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A204").add("Message", "SUBAUA configuration not found. Please contact system administrator.");

								JsonObject dataJsonObject = value2.build();
								model.addAttribute("model", dataJsonObject);
								return new ModelAndView("otpauthapi");
							} else {
								subAuaCode = subauaDetails.getSubaua_code().trim();

							}

							auth_data = AUAUtilities.doDecrypt(clientToken.getAut_token(), jsondata.trim());

							if (auth_data.contentEquals("A900")) {

								DateFormat rdateFormatt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
								Date rreqdatee1 = new Date();
								responsetime = rdateFormatt1.format(rreqdatee1);
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A117").add("Message", "Invalid encryption.");
								JsonObject dataJsonObject = value2.build();
								model.addAttribute("model", dataJsonObject);
								return new ModelAndView("otpauthapi");
							}

							Boolean isValid = true;
							Boolean isAadhaar = false;
							Boolean isclientValid = false;
							Boolean isAuth = false;
							Boolean isChannel = false;

							String fkey = "";

							Map map = new HashMap();
							JSONObject resobj = new JSONObject(auth_data);
							Iterator<?> keys = resobj.keys();
							while (keys.hasNext()) {
								fkey = (String) keys.next();
								map.put(fkey, resobj.get(fkey).toString());

								if (propertiesquery.getProperty(fkey) != null) {

									/** Checking valid aadhaar number **/
									paramValidation pval = new paramValidation();

									if (fkey.equalsIgnoreCase("channel")) {
										isChannel = pval.isChannel(resobj.get("channel").toString().trim());
									}

									if (fkey.equalsIgnoreCase("authtype")) {
										isAuth = pval.isAuthType(resobj.get("authtype").toString().trim());
									}

									if (fkey.equalsIgnoreCase("clientrefid")) {
										isClientref = resobj.get("clientrefid").toString().trim();
										isclientValid = pval.isClientId(resobj.get("clientrefid").toString().trim());
									}

									if (fkey.equalsIgnoreCase("uid")) {
										isAadhaar = pval.isAaadharValid(resobj.get("uid").toString());
									}
									isValid = true;
								} else {
									isValid = false;
									break;

								}
							}

							if (isValid == true) {

								if (isAadhaar == true) {

									if (isclientValid == true) {

										if (isAuth == true) {

											if (resobj.get("authtype").toString().trim().equalsIgnoreCase("1")) {
												if (isChannel == true) {

													String referenceid = "";
													referenceid = resobj.getString("clientrefid").trim().replaceAll("\\s", "");

													List<otpGeneration> unique_id = otpgenDao.getDuplicate_ID(mapheader.get("username"), referenceid);

													if (unique_id.size() > 0) {
														String response_time = "";
														DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
														Date reqdate2 = new Date();
														response_time = dateFormat2.format(reqdate2);
														Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + utransactionId + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
														Log.aua.info("Response Meta Data Details::Staus Message:Duplicate unique id" + "::" + "ResponseTime::" + response_time + "::Status Code:A111" + "::ResTranscation id:" + utransactionId);
														response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
														JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A111").add("Message", "Duplicate unique id.");
														JsonObject dataJsonObject = value2.build();
														model.addAttribute("model", dataJsonObject);
														return new ModelAndView("otpauthapi");

													} else {

														OtpProcessor opro = new OtpProcessor(PREAUAProperties.readAll(PREAUAProperties.getUidai_signing_cert()));
														opro.setUid(resobj.get("uid").toString().trim());
														opro.setAc(PREAUAProperties.getUidai_aua_code());
														opro.setSa(subAuaCode);
														if (resobj.get("uid").toString().length() == 12) {
															opro.setType(OtpType.AADHAAR_NUMBER);
														} else if (resobj.get("uid").toString().length() == 16) {
															opro.setType(OtpType.VIRTUAL_NUMBER);
														} else if (resobj.get("uid").toString().length() == 10) {
															opro.setType(OtpType.MOBILE_NUMBER);
														} else {
															opro.setType(OtpType.AADHAAR_NUMBER);
														}

														opro.setLk(PREAUAProperties.getUidai_license_key());
														if (resobj.get("channel").toString().trim().contentEquals("1")) {
															opro.setCh(ChannelType.SMS_ONLY);
														} else if (resobj.get("channel").toString().trim().contentEquals("2")) {
															opro.setCh(ChannelType.EMAIL_ONLY);
														} else if (resobj.get("channel").toString().trim().contentEquals("0")) {
															opro.setCh(ChannelType.SMS_AND_EMAIL);
														}

														oRequestXml = opro.getSignedXml(PREAUAProperties.readAll(PREAUAProperties.getClient_pfx()), PREAUAProperties.getClient_password());

														System.out.println("oRequestXml" + oRequestXml);

														odata = aesEncrypt.base64Encoded(oRequestXml);
														mhashData = aesEncrypt.hashSHA256(odata);
														hasingData = aesEncrypt.encrypt(mhashData, token);
														DateFormat dateFormat_csc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
														Date reqdate_csc = new Date();
														request_time_csc = dateFormat_csc.format(reqdate_csc);

														AsaRequest asaXml = new AsaRequest();

														asaXml.setClient_id(client_id);
														asaXml.setReq_data(odata);
														asaXml.setReq_hash(hasingData);
														asaXml.setClient_ts(request_time_csc);
														fXml = XMLUtils.objectToXML(asaXml);
														oResponseXml = HttpConnector.postData(PREAUAProperties.asa_request_url, fXml);

														String response_time = "";
														DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
														Date reqdate1 = new Date();
														response_time = dateFormat1.format(reqdate1);

														try {
															OtpRes res = opro.parse(oResponseXml);

															if (res.getRet().toString().equalsIgnoreCase("Y")) {

																response.setStatus(HttpServletResponse.SC_OK);
																Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + utransactionId + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
																Log.aua.info("Response Meta Data Details::Staus Message:OTP Generation Successfull" + "::" + "ResponseTime::" + response_time + "::Status Code:A200" + "::ResTranscation id:" + utransactionId);

																String[] items = res.getInfo().replaceAll("01", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
																List<String> container = Arrays.asList(items);

																MobileEmail me = opro.getMaskedMobileEmail(res);

																if (me.getEmail() == null && me.getMobileNumber() == null) {
																	msg_results = "OTP Generated successfully";

																} else if (me.getEmail() == null && me.getMobileNumber() != null) {
																	msg_results = String.format("OTP sent to Mobile Number %s", me.getMobileNumber());

																} else if (me.getEmail() != null && me.getMobileNumber() == null) {
																	msg_results = String.format("OTP sent to Email Id %s", me.getEmail());

																} else {
																	msg_results = String.format("OTP sent to Mobile Number %s Email Id %s", me.getMobileNumber(), me.getEmail());

																}

																otpservice.saveOtpGen(res, msg_results, utransactionId, resobj.get("clientrefid").toString(), request_time_csc, response_time, subAuaCode, mapheader.get("username"));

																JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A200");
																value2.add("ClientRefID", resobj.get("clientrefid").toString());
																value2.add("Message", msg_results);

																String iv = "1234567890!@#$%^";
																JsonObject dataJsonObject = value2.build();

																model.addAttribute("model", AUAUtilities.doEncrypt(clientToken.getAut_token(), iv, dataJsonObject.toString()));
																return new ModelAndView("otpauthapi");

															} else {

																response.setStatus(HttpServletResponse.SC_OK);
																Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
																Log.aua.info("Response Meta Data Details::Staus Message:" + properties.getProperty(res.getErr()) + "::" + "ResponseTime::" + response_time + "::Status Code:" + res.getErr() + "::ResTranscation id:" + res.getTxn());

																JsonObjectBuilder value2 = Json.createObjectBuilder();
																value2.add("StatusCode", "A201");
																value2.add("Error", res.getErr());
																value2.add("ClientRefID", resobj.get("clientrefid").toString());
																value2.add("Message", properties.getProperty(res.getErr()));

																otpservice.saveOtpGen(res, properties.getProperty(res.getErr()), utransactionId, resobj.get("clientrefid").toString(), request_time_csc, response_time, subAuaCode, mapheader.get("username"));

																JsonObject dataJsonObject = value2.build();
																String iv = "1234567890!@#$%^";
																model.addAttribute("model", AUAUtilities.doEncrypt(clientToken.getAut_token(), iv, dataJsonObject.toString()));
																return new ModelAndView("otpauthapi");

															}

														} catch (AuaServerException ex) {

															if (ex.getCode().equals("14")) {

																response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
																DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
																Date reqdate2 = new Date();
																response_time = dateFormat2.format(reqdate2);

																otpservice.saveOtpGenException(ex.getCode(), ex.getMessage(), utransactionId, resobj.get("clientrefid").toString(), request_time_csc, response_time, subAuaCode, mapheader.get("username"));

																JsonObjectBuilder value2 = Json.createObjectBuilder();
																value2.add("StatusCode", "A211");
																value2.add("Error", "998");
																if (StringUtils.isNotEmpty(isClientref.trim())) {
																	value2.add("ClientRefID", isClientref);
																}
																value2.add("Message", "Invalid Aadhaar Number/VID.");
																JsonObject dataJsonObject = value2.build();
																model.addAttribute("model", dataJsonObject);
																return new ModelAndView("otpauthapi");

															}

															otpservice.saveOtpGenException(ex.getCode(), ex.getMessage(), utransactionId, resobj.get("clientrefid").toString(), request_time_csc, response_time, subAuaCode, mapheader.get("username"));

															response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
															response_time = dateFormat1.format(reqdate1);

															Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + utransactionId + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
															Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "ResponseTime::" + response_time + "::Status Code:" + "A211" + "::ResTranscation id:" + utransactionId);

															JsonObjectBuilder value2 = Json.createObjectBuilder();
															value2.add("StatusCode", "A211");
															if (StringUtils.isNotEmpty(isClientref.trim())) {
																value2.add("ClientRefID", isClientref);
															}
															value2.add("Error", ex.getCode());
															value2.add("Message", ex.getMessage());
															JsonObject dataJsonObject = value2.build();
															model.addAttribute("model", dataJsonObject);
															return new ModelAndView("otpauthapi");

														} catch (UidaiSignatureVerificationFailedException ex) {
															response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
															response_time = dateFormat1.format(reqdate1);

															Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + utransactionId + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
															Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "ResponseTime::" + response_time + "::Status Code:" + "A214" + "::ResTranscation id:" + utransactionId);
															JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A214");

															value2.add("Message", ex.getMessage());
															JsonObject dataJsonObject = value2.build();
															model.addAttribute("model", dataJsonObject);
															return new ModelAndView("otpauthapi");
														}

														catch (Exception e) {

															DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
															Date reqdate2 = new Date();
															responsetime = dateFormat2.format(reqdate2);

															if (e.getMessage().contentEquals("Invalid uid")) {
																response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
																Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
																Log.aua.info("Response Meta Data Details::Staus Message:" + e.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:998" + "::ResTranscation id:" + "");
																JsonObjectBuilder value2 = Json.createObjectBuilder();

																otpservice.saveOtpGenException("A112", "998", utransactionId, resobj.get("clientrefid").toString(), request_time_csc, response_time, subAuaCode, mapheader.get("username"));

																value2.add("StatusCode", "A112").add("Error", "998").add("Message", "Invalid Aadhaar Number/VID.");
																if (StringUtils.isNotEmpty(isClientref.trim())) {
																	value2.add("ClientRefID", isClientref);
																}

																JsonObject dataJsonObject = value2.build();
																model.addAttribute("model", dataJsonObject);
																return new ModelAndView("otpauthapi");
															} else {
																response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
																Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
																Log.aua.info("Response Meta Data Details::Staus Message:" + e.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:A101" + "::ResTranscation id:" + "");

																otpservice.saveOtpGenException("A101", e.getMessage(), utransactionId, resobj.get("clientrefid").toString(), request_time_csc, response_time, subAuaCode, mapheader.get("username"));

																JsonObjectBuilder value2 = Json.createObjectBuilder();
																value2.add("StatusCode", "A101").add("Message", "Something went wrong. Please contact technical team.");

																if (StringUtils.isNotEmpty(isClientref.trim())) {
																	value2.add("ClientRefID", isClientref);
																}

																JsonObject dataJsonObject = value2.build();
																model.addAttribute("model", dataJsonObject);
																return new ModelAndView("otpauthapi");

															}

														}

													}

												} else {

													String response_time = "";
													DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
													Date reqdate2 = new Date();
													response_time = dateFormat2.format(reqdate2);

													Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
													Log.aua.info("Response Meta Data Details::Staus Message:Channel value not allowed" + "::" + "ResponseTime::" + response_time + "::Status Code:A117" + "::ResTranscation id:" + "");

													response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
													JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A117").add("Message", "Channel value is invalid/required.");
													JsonObject dataJsonObject = value2.build();
													model.addAttribute("model", dataJsonObject);
													return new ModelAndView("otpauthapi");
												}

											}

											else if (resobj.get("authtype").toString().trim().equalsIgnoreCase("2")) {

												if (StringUtils.isNotEmpty(resobj.getString("otpvalue"))) {

													Pattern testPattern = Pattern.compile("\\d{6}");
													Matcher teststring = testPattern.matcher(resobj.getString("otpvalue"));
													if (teststring.matches()) {

														String otpValue = "";

														List<otpGeneration> generationData = otpgenDao.getaadhaarNumber(resobj.getString("clientrefid"), mapheader.get("username"));

														otpValue = resobj.getString("otpvalue");

														request_time = dateFormat.format(reqdate);
														if (generationData.size() < 1) {

															String response_time = "";
															DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
															Date reqdate1 = new Date();
															response_time = dateFormat1.format(reqdate1);

															Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
															Log.aua.info("Response Meta Data Details::Staus Message:Unique id is not valid" + "::" + "ResponseTime::" + response_time + "::Status Code:A309" + "::ResTranscation id:" + "");

															response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
															JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A309").add("Message", "ClientRefID is not valid.");
															JsonObject dataJsonObject = value2.build();
															model.addAttribute("model", dataJsonObject);
															return new ModelAndView("otpauthapi");

														} else {

															String otp = "", uid = "", trid = "", refid = "", otpRequestXml = "";
															trid = generationData.get(0).getTRANSACTION_ID();
															refid = generationData.get(0).getREFERENCE_ID();

															AuthProcessor pro = new AuthProcessor(PREAUAProperties.readAll(PREAUAProperties.getUidai_encrypt_cert()), PREAUAProperties.readAll(PREAUAProperties.getUidai_signing_cert()));
															pro.setUid(resobj.get("uid").toString());
															pro.setAc(PREAUAProperties.getUidai_aua_code());
															pro.setSa(subAuaCode);
															pro.setRc(RcType.Y);
															pro.setTid(TidType.None);
															pro.setLk(PREAUAProperties.getUidai_license_key());
															pro.setTxn(trid);
															pro.prepareOtpPIDBlock(otpValue, "AUTH123");
															otpRequestXml = pro.getSignedXml(PREAUAProperties.readAll(PREAUAProperties.getClient_pfx()), PREAUAProperties.getClient_password(), utransactionId);

															String otpAuthData = aesEncrypt.base64Encoded(otpRequestXml);
															String otpHashData = aesEncrypt.hashSHA256(otpAuthData);
															String otphasingData = aesEncrypt.encrypt(otpHashData, token);
															DateFormat otpDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
															Date otpReqdate = new Date();
															String otpRequestTime = otpDateFormat.format(otpReqdate);
															AsaRequest otpAsaXml = new AsaRequest();
															otpAsaXml.setClient_id(client_id);
															otpAsaXml.setReq_data(otpAuthData);
															otpAsaXml.setReq_hash(otphasingData);
															otpAsaXml.setClient_ts(otpRequestTime);
															String otpfinalXml = XMLUtils.objectToXML(otpAsaXml);

															String otpResponseXml = HttpConnector.postData(PREAUAProperties.asa_request_url, otpfinalXml);

															try {

																AuthRes res = pro.parse(otpResponseXml);
																// END CSC authentication//

																String response_time = "";
																DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
																Date reqdate1 = new Date();
																response_time = dateFormat1.format(reqdate1);

																if (res.getRet().equalsIgnoreCase("Y")) {

																	int otpt = otpgenDao.updateOtpgen(mapheader.get("username"), refid.trim());

																	response.setStatus(HttpServletResponse.SC_OK);

																	String iv = "1234567890!@#$%^";
																	Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
																	Log.aua.info("Response Meta Data Details::Staus Message:Authentication Success" + "::" + "ResponseTime::" + response_time + "::Status Code:A200" + "::ResTranscation id:" + res.getTxn());
																	String[] items = res.getInfo().replaceAll("04", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
																	List<String> infoData = Arrays.asList(items);

																	otpservice.saveOtpVer(res, refid, request_time, response_time, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), "", utransactionId);

																	JsonObjectBuilder value2 = Json.createObjectBuilder();
																	value2.add("StatusCode", "A200");

																	value2.add("TransactionID", res.getTxn());
																	value2.add("UidToken", infoData.get(0));
																	value2.add("ClientRefID", refid);
																	value2.add("Message", "Authentication Success.");

																	JsonObject dataJsonObject = value2.build();
																	model.addAttribute("model", AUAUtilities.doEncrypt(clientToken.getAut_token(), iv, dataJsonObject.toString()));
																	return new ModelAndView("otpauthapi");

																} else {
																	response.setStatus(HttpServletResponse.SC_OK);
																	String[] items = res.getInfo().replaceAll("04", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
																	List<String> infoData = Arrays.asList(items);
																	Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
																	Log.aua.info("Response Meta Data Details::Staus Message:" + properties.getProperty(res.getErr()) + "::" + "ResponseTime::" + response_time + "::Status Code:" + res.getErr() + "::ResTranscation id:" + res.getTxn());
																	otpservice.saveOtpVer(res, refid, request_time, response_time, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), properties.getProperty(res.getErr()), utransactionId);

																	String iv = "1234567890!@#$%^";
																	JsonObjectBuilder value2 = Json.createObjectBuilder();
																	value2.add("StatusCode", "A201");
																	value2.add("UidToken", infoData.get(0));
																	value2.add("Error", res.getErr());
																	value2.add("TransactionID", res.getTxn());
																	value2.add("ClientRefID", refid);
																	value2.add("Message", properties.getProperty(res.getErr()));
																	JsonObject dataJsonObject = value2.build();
																	model.addAttribute("model", AUAUtilities.doEncrypt(clientToken.getAut_token(), iv, dataJsonObject.toString()));
																	return new ModelAndView("otpauthapi");

																}

															} catch (XMLParsingException ex) {
																response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
																String response_time = "";
																DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
																Date reqdate1 = new Date();
																response_time = dateFormat1.format(reqdate1);

																Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
																Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::ResTranscation id:" + "");

																JsonObjectBuilder value2 = Json.createObjectBuilder();
																value2.add("StatusCode", "A116");
																if (StringUtils.isNotEmpty(isClientref.trim())) {
																	value2.add("ClientRefID", isClientref);
																}

																value2.add("Message", "XML parsing problem.Please contact technical team.");
																JsonObject dataJsonObject = value2.build();
																model.addAttribute("model", dataJsonObject);
																return new ModelAndView("otpauthapi");

															} catch (AuaServerException ex) {

																DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
																Date reqdate2 = new Date();
																responsetime = dateFormat2.format(reqdate2);

																if (ex.getCode().equals("14")) {

																	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

																	Log.aua.info("User:::" + mapheader.get("subauacode") + "::Invalid SubAUA");
																	JsonObjectBuilder value2 = Json.createObjectBuilder();
																	if (StringUtils.isNotEmpty(isClientref.trim())) {
																		value2.add("ClientRefID", isClientref);
																	}
																	value2.add("StatusCode", "A112").add("Error", "998").add("Message", "Invalid Aadhaar Number/VID.");

																	JsonObject dataJsonObject = value2.build();
																	model.addAttribute("model", dataJsonObject);
																	return new ModelAndView("otpauthapi");

																}

																otpservice.saveExceptionOtp(ex.getCode(), ex.getMessage(), refid, request_time, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), utransactionId);

																response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

																Log.aua.info("User:::" + mapheader.get("subauacode") + "::Invalid SubAUA");
																JsonObjectBuilder value2 = Json.createObjectBuilder();
																value2.add("StatusCode", "A115");
																if (StringUtils.isNotEmpty(isClientref.trim())) {
																	value2.add("ClientRefID", isClientref);
																}

																// value2.add("Code", ex.getCode());
																// value2.add("Message", ex.getMessage());

																value2.add("Message", "Encountered technical problem.Please contact System Administrator.");

																JsonObject dataJsonObject = value2.build();
																model.addAttribute("model", dataJsonObject);
																return new ModelAndView("otpauthapi");

															} catch (Exception ex) {

																DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
																Date reqdate2 = new Date();
																responsetime = dateFormat2.format(reqdate2);

																if (ex.getMessage().contentEquals("Invalid uid")) {
																	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
																	Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
																	Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:998" + "::ResTranscation id:" + "");
																	JsonObjectBuilder value2 = Json.createObjectBuilder();

																	value2.add("StatusCode", "A112").add("Error", "998").add("Message", "Invalid Aadhaar Number/VID.");
																	if (StringUtils.isNotEmpty(isClientref.trim())) {
																		value2.add("ClientRefID", isClientref);
																	}

																	otpservice.saveExceptionOtp("998", ex.getMessage(), refid, request_time, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), utransactionId);

																	JsonObject dataJsonObject = value2.build();
																	model.addAttribute("model", dataJsonObject);
																	return new ModelAndView("otpauthapi");
																} else {
																	response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
																	Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
																	Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:A101" + "::ResTranscation id:" + "");

																	JsonObjectBuilder value2 = Json.createObjectBuilder();

																	value2.add("StatusCode", "A101").add("Message", "Something went wrong. Please contact technical team.");

																	otpservice.saveExceptionOtp("A101", ex.getMessage(), refid, request_time, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), utransactionId);

																	if (StringUtils.isNotEmpty(isClientref.trim())) {
																		value2.add("ClientRefID", isClientref);
																	}

																	JsonObject dataJsonObject = value2.build();
																	model.addAttribute("model", dataJsonObject);
																	return new ModelAndView("otpauthapi");

																}

															}

														}

													} else {

														response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
														DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
														Date reqdate2 = new Date();
														responsetime = dateFormat2.format(reqdate2);

														Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
														Log.aua.info("Response Meta Data Details::Staus Message:Invalid OTP Value" + "::" + "responsetime::" + responsetime + "::Status Code:A303" + "::ResTranscation id:" + "");
														JsonObjectBuilder value2 = Json.createObjectBuilder();
														value2.add("StatusCode", "A303").add("Message", "Invalid OTP Value.");
														value2.add("ClientRefID", isClientref);
														JsonObject dataJsonObject = value2.build();
														model.addAttribute("model", dataJsonObject);
														return new ModelAndView("otpauthapi");

													}

												} else {
													response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
													DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
													Date reqdate2 = new Date();
													responsetime = dateFormat2.format(reqdate2);

													Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
													Log.aua.info("Response Meta Data Details::Staus Message:Please check the parameter" + "::" + "responsetime::" + responsetime + "::Status Code:A303" + "::ResTranscation id:" + "");
													JsonObjectBuilder value2 = Json.createObjectBuilder();
													value2.add("StatusCode", "A304");
													value2.add("ClientRefID", isClientref);
													value2.add("Message", "OTP Value should not blank.");
													JsonObject dataJsonObject = value2.build();
													model.addAttribute("model", dataJsonObject);
													return new ModelAndView("otpauthapi");
												}

											} else {

												String response_time = "";
												DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
												Date reqdate2 = new Date();
												response_time = dateFormat2.format(reqdate2);
												Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
												Log.aua.info("Response Meta Data Details::Staus Message:Invalid authtype" + "::" + "ResponseTime::" + response_time + "::Status Code:A119" + "::ResTranscation id:" + "");
												response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
												JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A119").add("Message", "Invalid authtype.");
												JsonObject dataJsonObject = value2.build();
												model.addAttribute("model", dataJsonObject);
												return new ModelAndView("otpauthapi");

											}

										} else {
											String response_time = "";
											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											response_time = dateFormat2.format(reqdate2);
											Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
											Log.aua.info("Response Meta Data Details::Staus Message:Invalid authtype" + "::" + "ResponseTime::" + response_time + "::Status Code:A119" + "::ResTranscation id:" + "");
											response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
											JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A119").add("Message", "Invalid authtype.");
											JsonObject dataJsonObject = value2.build();
											model.addAttribute("model", dataJsonObject);
											return new ModelAndView("otpauthapi");

										}

									} else {

										response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
										DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
										Date reqdate2 = new Date();
										responsetime = dateFormat2.format(reqdate2);
										Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
										Log.aua.info("Response Meta Data Details::Staus Message:Client Reference Id is invalid" + "::" + "responsetime::" + responsetime + "::Status Code:A202" + "::ResTranscation id:" + "");

										JsonObjectBuilder value2 = Json.createObjectBuilder();
										value2.add("StatusCode", "A202");
										if (StringUtils.isNotEmpty(isClientref.trim())) {
											value2.add("ClientRefID", isClientref);
										}
										value2.add("Message", "ClientReferenceId is invalid.");

										JsonObject dataJsonObject = value2.build();
										model.addAttribute("model", dataJsonObject);
										return new ModelAndView("demographicauth");
									}

								} else {
									response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
									Date reqdate2 = new Date();
									responsetime = dateFormat2.format(reqdate2);

									Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
									Log.aua.info("Response Meta Data Details::Staus Message:Aadhaar Number should be of 12 digits" + "::" + "responsetime::" + responsetime + "::Status Code:A104" + "::ResTranscation id:" + "");
									JsonObjectBuilder value2 = Json.createObjectBuilder();
									value2.add("StatusCode", "A104");
									if (StringUtils.isNotEmpty(isClientref.trim())) {
										value2.add("ClientRefID", isClientref);
									}
									value2.add("Message", "Invalid UID/VID. Length Error (must be 12/16 digits).");
									JsonObject dataJsonObject = value2.build();
									model.addAttribute("model", dataJsonObject);
									return new ModelAndView("otpauthapi");
								}

							} else {

								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
								Date reqdate2 = new Date();
								responsetime = dateFormat2.format(reqdate2);

								Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
								Log.aua.info("Response Meta Data Details::Staus Message:Please check the parameter" + "::" + "responsetime::" + responsetime + "::Status Code:A103" + "::ResTranscation id:" + "");
								JsonObjectBuilder value2 = Json.createObjectBuilder();
								value2.add("ClientRefID", isClientref);
								value2.add("StatusCode", "A103").add("Message", "Please check the parameter.");

								JsonObject dataJsonObject = value2.build();
								model.addAttribute("model", dataJsonObject);
								return new ModelAndView("otpauthapi");
							}

						}

					} else {
						String response_time = "";
						DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						Date reqdate2 = new Date();
						response_time = dateFormat2.format(reqdate2);
						Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
						Log.aua.info("Response Meta Data Details::Staus Message:Bad request. Please check your headers." + "::" + "ResponseTime::" + response_time + "::Status Code:A100" + "::ResTranscation id:" + "");

						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A100");
						value2.add("Message", "Bad request. Please check your headers.");
						JsonObject dataJsonObject = value2.build();
						model.addAttribute("model", dataJsonObject);
						return new ModelAndView("otpauthapi");
					}

				} catch (Exception ex) {

					String response_time = "";
					DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					Date reqdate2 = new Date();
					response_time = dateFormat2.format(reqdate2);

					if (ex.getMessage() == null) {

						Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
						Log.aua.info("Response Meta Data Details::Staus Message:There is something technical issue! Please contact support team" + "::" + "ResponseTime::" + response_time + "::Status Code:A101" + "::ResTranscation id:" + "");

						response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

						JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A101");

						value2.add("Message", "Something went wrong. Please contact technical team.");
						if (StringUtils.isNotEmpty(isClientref.trim())) {
							value2.add("ClientRefID", isClientref);
						}

						JsonObject dataJsonObject = value2.build();
						model.addAttribute("model", dataJsonObject);
						return new ModelAndView("otpauthapi");
					}

					if (ex.getMessage() != null) {

						Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
						Log.aua.info("Response Meta Data Details::Staus Message:There is something technical issue! Please contact support team" + "::" + "ResponseTime::" + response_time + "::Status Code:A101" + "::ResTranscation id:" + "");

						response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

						JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A101");
						if (StringUtils.isNotEmpty(isClientref.trim())) {
							value2.add("ClientRefID", isClientref);
						}
						value2.add("Message", "Something went wrong. Please contact technical team.");
						JsonObject dataJsonObject = value2.build();
						model.addAttribute("model", dataJsonObject);
						return new ModelAndView("otpauthapi");

					}

				}

			} else {
				String response_time = "";
				DateFormat rdateFormatt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date rreqdatee1 = new Date();
				response_time = rdateFormatt1.format(rreqdatee1);

				Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
				Log.aua.info("Response Meta Data Details::Staus Message:Bad Request! Please check your headers." + "::" + "ResponseTime::" + response_time + "::Status Code:A100" + "::ResTranscation id:" + "");

				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				JsonObjectBuilder value2 = Json.createObjectBuilder();

				value2.add("StatusCode", "A100").add("Message", "Bad Request. Please check your headers.");
				if (StringUtils.isNotEmpty(isClientref.trim())) {
					value2.add("ClientRefID", isClientref);
				}
				JsonObject dataJsonObject = value2.build();
				model.addAttribute("model", dataJsonObject);
				return new ModelAndView("otpauthapi");
			}

		} else {

			String response_time = "";
			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date reqdate2 = new Date();
			response_time = dateFormat2.format(reqdate2);
			Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
			Log.aua.info("Response Meta Data Details::Staus Message:Invalid Request method" + "::" + "ResponseTime::" + response_time + "::Status Code:A105" + "::ResTranscation id:" + "");

			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

			JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A105");
			if (StringUtils.isNotEmpty(isClientref.trim())) {
				value2.add("ClientRefID", isClientref);
			}
			value2.add("Message", "Method not allowed.");
			JsonObject dataJsonObject = value2.build();
			model.addAttribute("model", dataJsonObject);
			return new ModelAndView("otpauthapi");
		}

		return new ModelAndView("otpauthapi");

	}

}
