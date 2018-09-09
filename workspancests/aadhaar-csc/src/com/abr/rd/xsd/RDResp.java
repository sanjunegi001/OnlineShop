package com.abr.rd.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Resp")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RDResp {
	@XmlAttribute(name = "errCode", required = true)
	private String errCode;
	@XmlAttribute(name = "fCount", required = false)
	private String fCount;
	@XmlAttribute(name = "fType", required = false)
	private String fType;
	@XmlAttribute(name = "nmPoints", required = false)
	private String nmPoints;
	@XmlAttribute(name = "iCount", required = false)
	private String iCount;
	@XmlAttribute(name = "iType", required = false)
	private String iType;
	@XmlAttribute(name = "pCount", required = false)
	private String pCount;
	@XmlAttribute(name = "pType", required = false)
	private String pType;
	@XmlAttribute(name = "qScore", required = false)
	private String qScore;

	public String getErrCode() {
		return this.errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getfCount() {
		return this.fCount;
	}

	public void setfCount(String fCount) {
		this.fCount = fCount;
	}

	public String getfType() {
		return this.fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}

	public String getNmPoints() {
		return this.nmPoints;
	}

	public void setNmPoints(String nmPoints) {
		this.nmPoints = nmPoints;
	}

	public String getiCount() {
		return this.iCount;
	}

	public void setiCount(String iCount) {
		this.iCount = iCount;
	}

	public String getqScore() {
		return this.qScore;
	}

	public void setqScore(String qScore) {
		this.qScore = qScore;
	}

	public String getiType() {
		return this.iType;
	}

	public void setiType(String iType) {
		this.iType = iType;
	}

	public String getpCount() {
		return this.pCount;
	}

	public void setpCount(String pCount) {
		this.pCount = pCount;
	}

	public String getpType() {
		return this.pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}
}