package com.abr.rd.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "Skey", propOrder = { "value" })
public class RDSkey {
	@XmlValue
	protected byte[] value;
	@XmlAttribute(name = "ci")
	protected String ci;

	public byte[] getValue() {
		return this.value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public String getCi() {
		return this.ci;
	}

	public void setCi(String value) {
		this.ci = value;
	}
}