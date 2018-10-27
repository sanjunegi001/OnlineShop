package com.hcl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.CustomerTo;
/*
 * This is the javadoc comment for the entire class. 
 * This class demonstrate the register customer  
 * @version 1.01 22/03/2016
*/

@Controller
public class RegistrationController {

	
	/*Show registration form*/
	@RequestMapping("/RegistrationForm")
	public ModelAndView showRegistrationform() {
		
		CustomerTo customerTo=new CustomerTo();
		return new ModelAndView("RegistrationForm","customerTo",customerTo);
		
	}
}
