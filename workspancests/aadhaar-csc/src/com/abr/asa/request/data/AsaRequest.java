package com.abr.asa.request.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "client_id", "req_hash", "req_data", "client_ts" })
public class AsaRequest {

	private String client_id;
	private String req_hash;
	private String req_data;
	private String client_ts;

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getReq_hash() {
		return req_hash;
	}

	public void setReq_hash(String req_hash) {
		this.req_hash = req_hash;
	}

	public String getReq_data() {
		return req_data;
	}

	public void setReq_data(String req_data) {
		this.req_data = req_data;
	}

	public String getClient_ts() {
		return client_ts;
	}

	public void setClient_ts(String client_ts) {
		this.client_ts = client_ts;
	}

}
