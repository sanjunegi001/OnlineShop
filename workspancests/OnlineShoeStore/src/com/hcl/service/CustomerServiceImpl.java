package com.hcl.service;

import java.sql.SQLException;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dao.CustomerDAO;
import com.hcl.model.CustomerTo;
/*
 * This is the javadoc comment for the class. 
 * This class  for customerService impl
 * @version 1.01 22/03/2016
*/

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDAO customerDAO;

	@Override
	@Transactional
	public int addCustomer(CustomerTo customerTo) {
		
		return customerDAO.addCustomer(customerTo);
		
	}

	@Override
	@Transactional
	public boolean isValidUser(String email, String password)
			throws SQLException {
		System.out.println("service to dao");
		return customerDAO.isValidUser(email, password) ;
	}

	/*@Override
	@Transactional
	public CustomerTo getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		System.out.println(email);
		return customerDAO.getCustomerrByEmail(email);
	}*/
	

	@Override
	@Transactional
	public List<CustomerTo> getAllCustomerInfo() {
		
		return customerDAO.getAllCustomerInfo();
	}

	@Override
	@Transactional
	public CustomerTo getCustomerByEmail(String email) {
		
		return customerDAO.getCustomerByEmail(email);
	}

	@Override
	@Transactional
	public void updateCustomer(CustomerTo customerTo) {
		customerDAO.updateCustomer(customerTo);
		
	}
	
	

}
