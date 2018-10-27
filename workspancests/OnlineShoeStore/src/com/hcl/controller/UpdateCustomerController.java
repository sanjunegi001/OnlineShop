package com.hcl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.CustomerTo;
import com.hcl.service.CustomerService;
import com.hcl.service.SearchService;
/*
 * This is the javadoc comment for the entire class. 
 * This class demonstrate the update customer  
 * @version 1.01 22/03/2016
*/
@Controller
public class UpdateCustomerController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SearchService searchService;
	
	/*Get all the data from db*/
	@ModelAttribute("customerList")
	public List<CustomerTo> populateCustomerList(){
		return customerService.getAllCustomerInfo();
	}
	
	/*Update form by email*/
	@RequestMapping(value="/updateCustomerByEmail")
	public String updateCustomerByEmail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("customerTo") CustomerTo customerTo,BindingResult result,ModelMap model){
		
		HttpSession session=request.getSession(false);
		String email=(String) session.getAttribute("uname");
		System.out.println(email);
		{
			CustomerTo cto=customerService.getCustomerByEmail(email);/*Get data by email*/
			model.addAttribute("customerData",cto);
			return "updateCustomerForm";
		}
		
	}
	/*Updated customer*/
	@RequestMapping(value="/updateCustomer",method=RequestMethod.POST)
	public String updateCustomer(@ModelAttribute CustomerTo customerTo,BindingResult result,ModelMap model){
		{
			customerService.updateCustomer(customerTo);
			String email=customerTo.getEmail();
			System.out.println(email);
			model.addAttribute("customerData",customerService.getCustomerByEmail(email));
			return "updateCustomer";
		}
		
		
	}
	

}
