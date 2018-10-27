package com.hcl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Customer;
import com.hcl.model.CustomerTo;
/*
 * This is the javadoc comment for the class. 
 * This class demonstrate the  customer  DAO impl
 * @version 1.01 22/03/2016
*/
@Repository
public class CustomerDAOImpl implements CustomerDAO{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void updateCustomer(CustomerTo customerTo) {
		Customer customer=new Customer();
		customer.setId(customerTo.getId());
		customer.setPassword(customerTo.getPassword());
		customer.setEmail(customerTo.getEmail());
		customer.setCustomerName(customerTo.getCustomerName());
		customer.setPhone(customerTo.getPhone());
		customer.setAddress(customerTo.getAddress());
		
		sessionFactory.getCurrentSession().update(customer);
		
	}

	@Override
	public int addCustomer(CustomerTo customerTo) {
		
		Customer customer=new Customer();
		
		customer.setId(customerTo.getId());
		customer.setCustomerName(customerTo.getCustomerName());
		customer.setEmail(customerTo.getEmail());
		customer.setPassword(customerTo.getPassword());
		customer.setPhone(customerTo.getPhone());
		customer.setAddress(customerTo.getAddress());
		
		int cid;
		sessionFactory.getCurrentSession().save(customer);
		
		return 0;
	}
	public boolean isValidUser(String email, String password) throws SQLException

    {
	 System.out.println(email+" "+password);
	 System.out.println("db");
        //String query = "Select * from customer_tab where email = ? and password = ?";

        PreparedStatement pstmt = dataSource.getConnection().prepareStatement("Select count(1) from customer where customer_email = ? and customer_password = ?");

        pstmt.setString(1, email);

        pstmt.setString(2, password);
        ResultSet resultSet = pstmt.executeQuery();
        System.out.println("resultset");
        if(resultSet.next())

            return (resultSet.getInt(1) > 0);
        

        else

           return false;

}

	@Override
	public List<CustomerTo> getAllCustomerInfo() {
		
		List<Customer>customerList=sessionFactory.getCurrentSession().createQuery("from Customer s").list();
		
		List<CustomerTo>customerToList=new ArrayList<CustomerTo>();
		
		for(Customer customer:customerList){
			CustomerTo customerTo=new CustomerTo();
			
			customerTo.setId(customer.getId());
			customerTo.setCustomerName(customer.getCustomerName());
			customerTo.setEmail(customer.getEmail());
			customerTo.setPassword(customer.getPassword());
			customerTo.setPhone(customer.getPhone());
			customerTo.setAddress(customer.getAddress());
			
			customerToList.add(customerTo);
		}
		
		return customerToList;
		
	}

	@Override
	public CustomerTo getCustomerByEmail(String email) {
		System.out.println("DAO");
		Customer customer=(Customer) sessionFactory.getCurrentSession().get(Customer.class,email);
		if(customer!=null){
			CustomerTo customerTo=new CustomerTo();
			//customerTo.setId(customer.getId());
			customerTo.setEmail(customer.getEmail());
			customerTo.setCustomerName(customer.getCustomerName());
			customerTo.setPassword(customer.getPassword());
			customerTo.setPhone(customer.getPhone());
			customerTo.setAddress(customer.getAddress());
			
			return customerTo;
		}else{
		return null;
		}
	}

	

}
