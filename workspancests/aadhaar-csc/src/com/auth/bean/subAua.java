package com.auth.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ab_subaua_details")
public class subAua {

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String subaua_code;

	@Column
	private String client_id;

	@Column
	private String client_name;

	@Column
	private String client_password;

	@Column
	private Date created_on;

	@Column
	private String env_type;

	@Column
	private int active_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubaua_code() {
		return subaua_code;
	}

	public void setSubaua_code(String subaua_code) {
		this.subaua_code = subaua_code;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_password() {
		return client_password;
	}

	public void setClient_password(String client_password) {
		this.client_password = client_password;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public int getactive_status() {
		return active_status;
	}

	public void setactive_status(int active_status) {
		this.active_status = active_status;
	}

	public int getPro_active_status() {
		return active_status;
	}

	public String getEnv_type() {
		return env_type;
	}

	public void setEnv_type(String env_type) {
		this.env_type = env_type;
	}

}
