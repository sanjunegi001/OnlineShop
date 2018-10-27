package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.model.CustomerTo;
import com.hcl.service.CustomerService;
/*
 * This is the javadoc comment for the entire class. 
 * This class demonstrate the view customer   
 * @version 1.01 22/03/2016
*/
@Controller
public class FetchAllCustomersController {
	@Autowired
	private CustomerService customerService;
	/*Fetch all the data from db and store in the list*/
	@ModelAttribute("customerList")
	public List<CustomerTo> populateCustomerList(){
		return customerService.getAllCustomerInfo();
	}
	/*Get all customer info*/
	@RequestMapping(value="/fetchAllCustomers",method=RequestMethod.GET)
	public String showAllScholars(ModelMap model){
		model.addAttribute("customerList",customerService.getAllCustomerInfo());
		return "fetchCustomersDetails";
	}

}
