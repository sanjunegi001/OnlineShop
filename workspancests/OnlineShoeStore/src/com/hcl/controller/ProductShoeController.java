package com.hcl.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.service.SearchService;

/*
 * This is the javadoc comment for the entire class. 
 * This class demonstrate the display product page  
 * @version 1.01 22/03/2016
*/

@Controller
public class ProductShoeController {
	@Autowired
	private SearchService searchService;
	/*Product page controller*/
	@RequestMapping(value="/showUpdate")
	public ModelAndView showUpdate(){
		
		return  new ModelAndView("Product","imageList",searchService.getAllImage());
	}
	
}
