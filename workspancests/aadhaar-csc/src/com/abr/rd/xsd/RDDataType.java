package com.abr.rd.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DataType")
@XmlEnum
public enum RDDataType {
	X, P;

	public final String value() {
		return name();
	}

	public static RDDataType fromValue(String v) {
		return valueOf(v);
	}
}