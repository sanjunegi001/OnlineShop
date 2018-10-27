package com.hcl.service;

import java.sql.SQLException;
import java.util.List;

import com.hcl.model.CustomerTo;
/*
 * This is the javadoc comment for the interface. 
 * This interface  for customerService
 * @version 1.01 22/03/2016
*/
public interface CustomerService {
	
	public int addCustomer(CustomerTo customerTo);//add the customer
	public List<CustomerTo> getAllCustomerInfo();
	public void updateCustomer(CustomerTo customerTo);
	public CustomerTo getCustomerByEmail(String email); //Email validation
	public boolean isValidUser(String email, String password) throws SQLException;

}
