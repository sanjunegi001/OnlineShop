package com.abr.rd.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Data")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RDData {
	@XmlValue
	protected byte[] value;
	@XmlAttribute(name = "type")
	protected RDDataType type;

	public byte[] getValue() {
		return this.value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public RDDataType getType() {
		if (this.type == null) {
			return RDDataType.X;
		}
		return this.type;
	}

	public void setType(RDDataType value) {
		this.type = value;
	}
}