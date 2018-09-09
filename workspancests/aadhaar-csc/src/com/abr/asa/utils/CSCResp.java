package com.abr.asa.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xml")
public class CSCResp {
	@XmlElement(name = "client_id")
	protected String client_id;

	@XmlElement(name = "res_code")
	protected String res_code;

	@XmlElement(name = "res_data")
	protected String res_data;

	@XmlElement(name = "res_hash")
	protected String res_hash;

	@XmlElement(name = "res_msg")
	protected String res_msg;

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_data() {
		return res_data;
	}

	public void setRes_data(String res_data) {
		this.res_data = res_data;
	}

	public String getRes_hash() {
		return res_hash;
	}

	public void setRes_hash(String res_hash) {
		this.res_hash = res_hash;
	}

	public String getRes_msg() {
		return res_msg;
	}

	public void setRes_msg(String res_msg) {
		this.res_msg = res_msg;
	}

}
