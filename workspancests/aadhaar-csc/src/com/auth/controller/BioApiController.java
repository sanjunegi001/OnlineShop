package com.auth.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
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
import com.abr.asa.processor.aesEncrypt;
import com.abr.asa.request.data.AsaRequest;
import com.abr.aua.pidgen.support.DemoAuthData;
import com.abr.aua.utils.XMLUtils;
import com.abr.exceptions.AuaServerException;
import com.abr.exceptions.InvalidResponseException;
import com.abr.exceptions.PidServerException;
import com.abr.exceptions.UidaiSignatureVerificationFailedException;
import com.abr.exceptions.XMLParsingException;
import com.auth.bean.deviceDetails;
import com.auth.bean.subAua;
import com.auth.bean.tokenDetails;
import com.auth.dao.BioDAO;
import com.auth.dao.SubAuaDAO;
import com.auth.dao.TokenDetailsDAO;
import com.auth.service.BioService;
import com.auth.utils.AUAUtilities;
import com.auth.utils.Log;
import com.auth.utils.PREAUAProperties;
import com.auth.utils.authData;
import com.auth.utils.paramValidation;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;

@Controller
public class BioApiController {

	@Autowired
	private SubAuaDAO subauadao;

	@Autowired
	private BioService bioService;

	@Autowired
	private TokenDetailsDAO tokendetailsdao;

	@Autowired
	private BioDAO bioDAO;

	@RequestMapping(value = "/v2/bioapi", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView bioapi(@RequestBody String jsondata, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws FileNotFoundException, IOException {

		// Global Variables
		String requestXml = "", isClientref = "", responseXml = "", asaData = "", hashData = "", encryptedhashData = "", auth_data = "", requesttime, responsetime = "", aadharcardnumber = "", verifyby = "", subAuaCode = "", token = "", request_time = "", asaRequestXml = "";

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
				/** Aadhaar errorcode checked **/
				ClassLoader classloadererror = Thread.currentThread().getContextClassLoader();
				properties.load(new FileInputStream(new File(classloadererror.getResource("aadhaarErrorCode.properties").getFile())));

				String flocation = "", fpostalcode = "", fcity = "";
				String orgip = AUAUtilities.getClientIpAddr(request);
				/** Ipaddress captured **/
				String geofile = PREAUAProperties.getGeofile();
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
							return new ModelAndView("biometricauth");

						} else {

							subAua subauaDetails = subauadao.getSubAUA(mapheader.get("username"));
							tokenDetails clientToken = tokendetailsdao.getOneById(mapheader.get("username"));

							if (subauaDetails == null) {
								response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
								Log.aua.info("User:::" + mapheader.get("subauacode") + "::Invalid SubAUA");
								JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A204").add("Message", "SUBAUA configuration not found. Please contact system administrator.");

								JsonObject dataJsonObject = value2.build();
								model.addAttribute("model", dataJsonObject);
								return new ModelAndView("biometricauth");
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
								return new ModelAndView("biometricauth");
							}

							Boolean isValid = true;
							Boolean isAadhaar = false;
							Boolean isclientValid = true;

							DemoAuthData demoauthdata = new DemoAuthData();
							authData auth = new authData();
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

										String regcode = "", mi = "", datecreated = "", fudc = "";
										org.jsoup.nodes.Document doc2 = null;
										doc2 = Jsoup.parse(resobj.get("biometricdata").toString().trim(), "", Parser.xmlParser());

										fudc = doc2.getElementsByTag("DeviceInfo").attr("dc").trim();

										int isValidDevice = bioDAO.isValidDevice(fudc);

										if (isValidDevice != 1) {
											regcode = doc2.getElementsByTag("DeviceInfo").attr("dc").trim();
											mi = doc2.getElementsByTag("DeviceInfo").attr("mi").trim();
											DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate3 = new Date();
											datecreated = dateFormat3.format(reqdate3);

											deviceDetails dedetails = new deviceDetails();

											dedetails.setUDC(fudc);
											dedetails.setMCNAME(mi);
											dedetails.setSTATUS("1");
											dedetails.setSERIALNUMBER("NA");
											dedetails.setCREATEDON(datecreated);
											dedetails.setCLIENTID(mapheader.get("username"));
											int deviceid = bioDAO.save(dedetails);

										}

										aadharcardnumber = resobj.get("uid").toString().trim();
										AuthProcessor pro = null;
										pro = new AuthProcessor(PREAUAProperties.readAll(PREAUAProperties.getUidai_encrypt_cert()));

										pro.setUid(aadharcardnumber.toString().trim());
										pro.setAc(PREAUAProperties.getUidai_aua_code());
										pro.setSa(subAuaCode);
										pro.setRc(RcType.Y);
										pro.setTid(TidType.registered);
										pro.setLk(PREAUAProperties.getUidai_bio_license_key());
										pro.setRDRespone(resobj.get("biometricdata").toString().trim(), "FMR", false, false, false, false, false, "UDC0001");

										try {

											requestXml = pro.getSignedXml(PREAUAProperties.readAll(PREAUAProperties.getClient_pfx()), PREAUAProperties.getClient_password(), utransactionId);

											asaData = aesEncrypt.base64Encoded(requestXml);
											hashData = aesEncrypt.hashSHA256(asaData);
											encryptedhashData = aesEncrypt.encrypt(hashData, PREAUAProperties.asa_token);
											AsaRequest asaXml = new AsaRequest();

											asaXml.setClient_id(PREAUAProperties.asa_clientid);
											asaXml.setReq_data(asaData);
											asaXml.setReq_hash(encryptedhashData);
											asaXml.setClient_ts(requesttime);

											asaRequestXml = XMLUtils.objectToXML(asaXml);
											responseXml = HttpConnector.postData(PREAUAProperties.asa_request_url, asaRequestXml);

											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											responsetime = dateFormat2.format(reqdate2);

											AuthRes res = pro.parse(responseXml);

											if (res.getRet().equalsIgnoreCase("Y")) {
												Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + subAuaCode + "::" + "ReqTransactionId::" + utransactionId + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
												Log.aua.info("Response Meta Data Details::Staus Message:Authentication Success" + "::" + "responsetime::" + responsetime + "::Status Code:A200" + "::ResTranscation id:" + res.getTxn());
												response.setStatus(HttpServletResponse.SC_OK);

												String[] items = res.getInfo().replaceAll("04", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
												List<String> infoData = Arrays.asList(items);

												bioService.saveBio(res, requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), map, utransactionId);

												JsonObjectBuilder value2 = Json.createObjectBuilder();
												value2.add("StatusCode", "A200");

												if (StringUtils.isNotEmpty(isClientref.trim())) {
													value2.add("ClientRefID", isClientref);
												}

												value2.add("TransactionID", res.getTxn());
												value2.add("UidToken", infoData.get(0));
												value2.add("Message", "Authentication Success");
												JsonObject dataJsonObject = value2.build();
												String iv = "1234567890!@#$%^";
												model.addAttribute("model", AUAUtilities.doEncrypt(clientToken.getAut_token(), iv, dataJsonObject.toString()));
												return new ModelAndView("biometricauth");

											}

											else {
												Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "ReqTransactionId::" + utransactionId + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
												Log.aua.info("Response Meta Data Details::Staus Message:" + properties.getProperty(res.getErr()) + "::" + "responsetime::" + responsetime + "::Status Code: A201" + "::Error Code:" + res.getErr() + "::ResTranscation id:" + res.getTxn());
												response.setStatus(HttpServletResponse.SC_OK);

												String[] items = res.getInfo().replaceAll("04", "").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
												List<String> infoData = Arrays.asList(items);

												bioService.saveBio(res, requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, mapheader.get("username"), map, utransactionId);

												JsonObjectBuilder value2 = Json.createObjectBuilder();
												value2.add("StatusCode", "A201");
												if (StringUtils.isNotEmpty(isClientref.trim())) {
													value2.add("ClientRefID", isClientref);
												}
												value2.add("TransactionID", res.getTxn());
												value2.add("UidToken", infoData.get(0));
												value2.add("Error", res.getErr());
												value2.add("Message", properties.getProperty(res.getErr()));
												JsonObject dataJsonObject = value2.build();
												String iv = "1234567890!@#$%^";
												model.addAttribute("model", AUAUtilities.doEncrypt(clientToken.getAut_token(), iv, dataJsonObject.toString()));
												return new ModelAndView("demographicauth");

											}

										} catch (XMLParsingException ex) {

											response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											responsetime = dateFormat2.format(reqdate2);

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

											return new ModelAndView("biometricauth");

										} catch (AuaServerException ex) {

											if (ex.getCode().equals("14")) {
												response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
												DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
												Date reqdate2 = new Date();
												responsetime = dateFormat2.format(reqdate2);

												bioService.saveExceptionBIO(ex.getCode(), ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, verifyby, map, utransactionId);

												Log.aua.info("User:::" + mapheader.get("subauacode") + "::Invalid SubAUA");
												JsonObjectBuilder value2 = Json.createObjectBuilder();
												if (StringUtils.isNotEmpty(isClientref.trim())) {
													value2.add("ClientRefID", isClientref);
												}
												value2.add("StatusCode", "A112").add("Error", "998").add("Message", "Invalid Aadhaar Number/VID.");

												JsonObject dataJsonObject = value2.build();
												model.addAttribute("model", dataJsonObject);
												return new ModelAndView("biometricauth");

											}
											response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											responsetime = dateFormat2.format(reqdate2);

											bioService.saveExceptionBIO(ex.getCode(), ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, verifyby, map, utransactionId);

											Log.aua.info("User:::" + mapheader.get("subauacode") + "::Invalid SubAUA");
											JsonObjectBuilder value2 = Json.createObjectBuilder();
											if (StringUtils.isNotEmpty(isClientref.trim())) {
												value2.add("ClientRefID", isClientref);
											}
											value2.add("StatusCode", "A115").add("Message", "Encountered technical problem.Please contact System Administrator.");

											JsonObject dataJsonObject = value2.build();
											model.addAttribute("model", dataJsonObject);
											return new ModelAndView("biometricauth");

										} catch (InvalidResponseException ex) {

											response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);

											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											responsetime = dateFormat2.format(reqdate2);
											org.jsoup.nodes.Document doc = Jsoup.parse("<?xml version=\"1.0\" encoding=\"UTF-8\">" + responseXml, "", Parser.xmlParser());

											bioService.saveExceptionBIO("A114", ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, verifyby, map, utransactionId);

											Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
											Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:" + ((Element) doc).select("Code").text() + "::ResTranscation id:" + "");

											JsonObjectBuilder value2 = Json.createObjectBuilder();
											if (StringUtils.isNotEmpty(isClientref.trim())) {
												value2.add("ClientRefID", isClientref);
											}
											value2.add("StatusCode", "A114").add("Error", ((Element) doc).select("Code").text()).add("Message", "Invalid response.");
											JsonObject dataJsonObject = value2.build();
											model.addAttribute("model", dataJsonObject);
											return new ModelAndView("biometricauth");

										} catch (UidaiSignatureVerificationFailedException ex) {

											response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											responsetime = dateFormat2.format(reqdate2);
											org.jsoup.nodes.Document doc = Jsoup.parse("<?xml version=\"1.0\" encoding=\"UTF-8\">" + responseXml, "", Parser.xmlParser());

											// demoService.saveExceptionDemo(((Element) doc).select("Code").text(), ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, verifyby, subAuaCode, map, utransactionId);

											bioService.saveExceptionBIO(((Element) doc).select("Code").text(), ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, verifyby, map, utransactionId);

											Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
											Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:" + ((Element) doc).select("Code").text() + "::ResTranscation id:" + "");
											JsonObjectBuilder value2 = Json.createObjectBuilder();
											if (StringUtils.isNotEmpty(isClientref.trim())) {
												value2.add("ClientRefID", isClientref);
											}
											value2.add("StatusCode", "A113").add("Error", ((Element) doc).select("Code").text()).add("Message", "Signature verification failed.");
											JsonObject dataJsonObject = value2.build();
											model.addAttribute("model", dataJsonObject);
											return new ModelAndView("biometricauth");

										}

										catch (Exception ex) {

											DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											Date reqdate2 = new Date();
											responsetime = dateFormat2.format(reqdate2);
											if (ex.getMessage().contentEquals("Invalid uid")) {

												response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
												Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
												Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:998" + "::ResTranscation id:" + "");
												// demoService.saveExceptionDemo("998", ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, verifyby, subAuaCode, map, utransactionId);

												bioService.saveExceptionBIO("998", ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, verifyby, map, utransactionId);

												JsonObjectBuilder value2 = Json.createObjectBuilder();
												if (StringUtils.isNotEmpty(isClientref.trim())) {
													value2.add("ClientRefID", isClientref);
												}
												value2.add("StatusCode", "A112").add("Error", "998").add("Message", "Invalid Aadhaar Number/VID.");
												JsonObject dataJsonObject = value2.build();
												model.addAttribute("model", dataJsonObject);
												return new ModelAndView("biometricauth");

											} else {

												response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

												Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
												Log.aua.info("Response Meta Data Details::Staus Message:" + ex.getMessage() + "::" + "responsetime::" + responsetime + "::Status Code:A101" + "::ResTranscation id:" + "");

												bioService.saveExceptionBIO("A101", ex.getMessage(), requesttime, responsetime, flocation, orgip, fcity, fpostalcode, subAuaCode, verifyby, map, utransactionId);

												JsonObjectBuilder value2 = Json.createObjectBuilder();

												if (StringUtils.isNotEmpty(isClientref.trim())) {
													value2.add("ClientRefID", isClientref);
												}
												value2.add("StatusCode", "A101").add("Message", "Something went wrong. Please contact technical team.");
												JsonObject dataJsonObject = value2.build();
												model.addAttribute("model", dataJsonObject);
												return new ModelAndView("biometricauth");

											}

										}

									} else {
										response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
										DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
										Date reqdate2 = new Date();
										responsetime = dateFormat2.format(reqdate2);
										Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
										Log.aua.info("Response Meta Data Details::Staus Message:Client Reference Id is invalid" + "::" + "responsetime::" + responsetime + "::Status Code:A111" + "::ResTranscation id:" + "");

										JsonObjectBuilder value2 = Json.createObjectBuilder();
										value2.add("StatusCode", "A202");
										if (StringUtils.isNotEmpty(isClientref.trim())) {
											value2.add("ClientRefID", isClientref);
										}
										value2.add("Message", "ClientReferenceId is invalid.");

										JsonObject dataJsonObject = value2.build();
										model.addAttribute("model", dataJsonObject);
										return new ModelAndView("biometricauth");
									}

								}

								else {
									response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
									Date reqdate2 = new Date();
									responsetime = dateFormat2.format(reqdate2);

									Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
									Log.aua.info("Response Meta Data Details::Staus Message:Aadhaar Number should be of 12 digits" + "::" + "responsetime::" + responsetime + "::Status Code:A110" + "::ResTranscation id:" + "");

									JsonObjectBuilder value2 = Json.createObjectBuilder();
									if (StringUtils.isNotEmpty(isClientref.trim())) {
										value2.add("ClientRefID", isClientref);
									}
									value2.add("StatusCode", "A110").add("Message", "Invalid UID/VID. Length Error (must be 12/16 digits).");
									JsonObject dataJsonObject = value2.build();
									model.addAttribute("model", dataJsonObject);
									return new ModelAndView("biometricauth");
								}

							} else {
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
								Date reqdate2 = new Date();
								responsetime = dateFormat2.format(reqdate2);

								Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "RequestTime::" + requesttime + "::" + "API Name::" + "2.5");
								Log.aua.info("Response Meta Data Details::Staus Message:Please check the parameter" + "::" + "responsetime::" + responsetime + "::Status Code:A103" + "::ResTranscation id:" + "");
								JsonObjectBuilder value2 = Json.createObjectBuilder();
								if (StringUtils.isNotEmpty(isClientref.trim())) {
									value2.add("ClientRefID", isClientref);
								}
								value2.add("StatusCode", "A103").add("Message", "Please check the parameter.");

								JsonObject dataJsonObject = value2.build();
								model.addAttribute("model", dataJsonObject);
								return new ModelAndView("biometricauth");
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
						JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A100").add("Message", "Bad request. Please check your headers.");
						JsonObject dataJsonObject = value2.build();
						model.addAttribute("model", dataJsonObject);
						return new ModelAndView("biometricauth");
					}

				} catch (PidServerException ex) {

					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					JsonObjectBuilder value2 = Json.createObjectBuilder();
					value2.add("StatusCode", "A208");

					if (StringUtils.isNotEmpty(isClientref.trim())) {
						value2.add("ClientRefID", isClientref);
					}

					value2.add("Message", "Invalid pidxml.");
					JsonObject dataJsonObject = value2.build();
					model.addAttribute("model", dataJsonObject);
					return new ModelAndView("biometricauth");

				} catch (JSONException e) {

					String response_time = "";
					DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					Date reqdate2 = new Date();
					response_time = dateFormat2.format(reqdate2);

					Log.aua.info("Request Meta Data Details:" + "AUA Code::" + PREAUAProperties.uidai_aua_code + "::" + "SUB AUA Code::" + mapheader.get("subauacode") + "::" + "ReqTransactionId::" + "" + "::" + "RequestTime::" + request_time + "::" + "API Name::" + "2.5");
					Log.aua.info("Response Meta Data Details::Staus Message:Invalid Json" + "::" + "ResponseTime::" + response_time + "::Status Code:A102" + "::ResTranscation id:" + "");
					// Demo log

					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					JsonObjectBuilder value2 = Json.createObjectBuilder().add("StatusCode", "A102");
					if (StringUtils.isNotEmpty(isClientref.trim())) {
						value2.add("ClientRefID", isClientref);
					}
					value2.add("Message", "Invalid Json.");
					JsonObject dataJsonObject = value2.build();
					model.addAttribute("model", dataJsonObject);
					return new ModelAndView("biometricauth");

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
						if (StringUtils.isNotEmpty(isClientref.trim())) {
							value2.add("ClientRefID", isClientref);
						}
						value2.add("Message", "Something went wrong. Please contact technical team.");
						JsonObject dataJsonObject = value2.build();
						model.addAttribute("model", dataJsonObject);
						return new ModelAndView("biometricauth");
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
						return new ModelAndView("biometricauth");

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
				if (StringUtils.isNotEmpty(isClientref.trim())) {
					value2.add("ClientRefID", isClientref);
				}
				value2.add("StatusCode", "A100").add("Message", "Bad Request. Please check your headers.");
				JsonObject dataJsonObject = value2.build();
				model.addAttribute("model", dataJsonObject);
				return new ModelAndView("biometricauth");
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
			return new ModelAndView("biometricauth");
		}

		return new ModelAndView("biometricauth");

	}

}
