package com.hcl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/*
 * This is the javadoc comment for the class. 
 * This class is entity class for Customer
 * @version 1.01 22/03/2016
*/


@Entity
@Table(name="customer")
public class Customer {
	
	@Column(name="CUSTOMER_ID")
	private String id;

	@Column(name="CUSTOMER_NAME")
	private String customerName;
	@Id
	@Column(name="CUSTOMER_EMAIL")
	private String email;
	@Column(name="CUSTOMER_PASSWORD")
	private String password;
	@Column(name="CUSTOMER_PHONE")
	private String phone;
	@Column(name="CUSTOMER_ADDRESS")
	private String address;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
