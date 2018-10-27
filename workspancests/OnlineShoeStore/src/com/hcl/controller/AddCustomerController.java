package com.hcl.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

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
 * This class demonstrate the add customer  
 * @version 1.01 22/03/2016
*/

@Controller
public class AddCustomerController {
	
	@Autowired
	private Validator validator;
	@Autowired
	private SearchService searchService;
	@Autowired
	private CustomerService customerService;
	/*Show update customer form*/
	@RequestMapping(value="/showCustomerInfo",method=RequestMethod.GET)
	public ModelAndView showCustomerInfo(){
		CustomerTo customerTo=new CustomerTo();
		return new ModelAndView("customerDetails","customerTo",customerTo);
	}
	
	@RequestMapping(value="/customerDetails",method=RequestMethod.POST)
	public String addCustomerDetails(@ModelAttribute  CustomerTo customerTo,BindingResult result,ModelMap model){
		
		Set<ConstraintViolation<CustomerTo>> failures=validator.validate(customerTo);
		{
			Integer cid=customerService.addCustomer(customerTo);
			String message="You registered successfully with customer Id:"+cid;
			model.addAttribute("message", message);
			return "RegistrationForm";
		}
		
	}
	/*Add the customer to the database*/
	@RequestMapping(value="/addCustomer",method=RequestMethod.POST)
	public String addCustomerForm(@ModelAttribute CustomerTo customerTo,BindingResult result,ModelMap model)
	{
		{
			customerService.addCustomer(customerTo);
			String message="DATA SUCCESSFULLY PERSISTED";
			model.addAttribute("message",message);
			return "Login";
		}
	}
	/*Show the login form*/
	@RequestMapping(value="/showLoginForm",method=RequestMethod.GET)
	public ModelAndView showLoginForm()
	{
		CustomerTo customerTo=new CustomerTo();
		return new ModelAndView("Login","customerTo",customerTo);
	}
	
	
	/*Check for the validation of user using database*/
	@RequestMapping(value="/loginUser",method=RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("customerTo")CustomerTo customerTo)
	{
		 ModelAndView model= null;
		 try
		 {
			 System.out.println("Fetch"+customerTo.getEmail()+" "+customerTo.getPassword());
			 
			 boolean isValidUser =customerService.isValidUser(customerTo.getEmail(),customerTo.getPassword());
			 if(isValidUser)
			 {
				 System.out.println("User Login Successful");
				 request.setAttribute("loggedInUser", customerTo.getCustomerName());
				 String message=customerTo.getEmail();
				 HttpSession session=request.getSession();
				 session.setAttribute("uname",message);
				 System.out.println(customerTo.getEmail());
				 model = new ModelAndView("Product","imageList",searchService.getAllImage());/*Show product after the valid user*/
			
				 
			 }
			 else
			 {
				 model = new ModelAndView("invalid");/*If invalid show invalid form*/
				 model.addObject("customerTo",customerTo);
				 request.setAttribute("message", "Invalid credentials!!");
			 }

		 }catch(Exception e)
		 {
			 e.printStackTrace(); 
		 }
		 return model;
	}

	
}
