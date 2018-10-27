package com.hcl.dao;

import java.sql.SQLException;
import java.util.List;

import com.hcl.model.CustomerTo;
/*
 * This is the javadoc comment for the interface class. 
 * This interface demonstrate the add customer  
 * @version 1.01 22/03/2016
*/
public interface CustomerDAO {
	
	public int addCustomer(CustomerTo customerTo);
	public List<CustomerTo> getAllCustomerInfo();
	public CustomerTo getCustomerByEmail(String email);
	public void updateCustomer(CustomerTo customerTo);
	public boolean isValidUser(String email, String password) throws SQLException;


}
