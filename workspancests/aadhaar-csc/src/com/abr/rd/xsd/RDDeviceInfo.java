package com.abr.rd.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeviceInfo")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RDDeviceInfo {
	@XmlAttribute(name = "dc", required = true)
	private String dc;
	@XmlAttribute(name = "dpId", required = true)
	private String dpId;
	@XmlAttribute(name = "mc", required = true)
	private byte[] mc;
	@XmlAttribute(name = "mi", required = true)
	private String mi;
	@XmlAttribute(name = "rdsId", required = true)
	private String rdsId;
	@XmlAttribute(name = "rdsVer", required = true)
	private String rdsVer;
	@XmlAttribute(name = "srno", required = true)
	private String srno;
	@XmlAttribute(name = "USBID", required = true)
	private String usbId;

	public String getDc() {
		return this.dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getDpId() {
		return this.dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

	public byte[] getMc() {
		return this.mc;
	}

	public void setMc(byte[] mc) {
		this.mc = mc;
	}

	public String getMi() {
		return this.mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getRdsId() {
		return this.rdsId;
	}

	public void setRdsId(String rdsId) {
		this.rdsId = rdsId;
	}

	public String getRdsVer() {
		return this.rdsVer;
	}

	public void setRdsVer(String rdsVer) {
		this.rdsVer = rdsVer;
	}

	public String getSrno() {
		return this.srno;
	}

	public void setSrno(String srno) {
		this.srno = srno;
	}

	public String getUsbId() {
		return this.usbId;
	}

	public void setUsbId(String usbId) {
		this.usbId = usbId;
	}
}