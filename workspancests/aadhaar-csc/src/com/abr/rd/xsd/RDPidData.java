package com.abr.rd.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PidData")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RDPidData {
	@XmlElement(name = "DeviceInfo", required = true)
	private RDDeviceInfo deviceInfo;
	@XmlElement(name = "Data", required = true)
	private RDData data;
	@XmlElement(name = "Skey", required = true)
	private RDSkey Skey;
	@XmlElement(name = "Hmac", required = true)
	private byte[] hmac;
	@XmlElement(name = "Resp", required = true)
	private RDResp resp;

	public RDDeviceInfo getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(RDDeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public RDData getData() {
		return this.data;
	}

	public void setData(RDData data) {
		this.data = data;
	}

	public RDSkey getSkey() {
		return this.Skey;
	}

	public void setSkey(RDSkey skey) {
		this.Skey = skey;
	}

	public byte[] getHmac() {
		return this.hmac;
	}

	public void setHmac(byte[] hmac) {
		this.hmac = hmac;
	}

	public RDResp getResp() {
		return this.resp;
	}

	public void setResp(RDResp resp) {
		this.resp = resp;
	}
}