package com.hcl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.BrandTO;
import com.hcl.model.ProductTO;
import com.hcl.service.SearchService;
/*
 * This is the javadoc comment for the entire class. 
 * This class demonstrate the search shoe  
 * @version 1.01 22/03/2016
*/
@Controller
public class SearchShoeController {
	
	@Autowired
	private SearchService searchService;
	/*List the product in the database*/
	@ModelAttribute("imageList")
	public List<ProductTO> getData(){
		return searchService.getAllImage();
	}
	/*List the brands present in the database*/
	@ModelAttribute("brandList")
	public List<BrandTO> getBrand(){
		return searchService.getAllBrand();
	}
	/*Show the search page*/
	@RequestMapping(value="SearchEngine")
	public String searchParticularProduct(HttpServletRequest request,ModelMap model){
		String productName=request.getParameter("search");
		ProductTO productTO=new ProductTO();
		model.addAttribute("imageList", searchService.getProductByName(productName));
		return "ProductName";
	}
	/*Show Product by product Id*/
	@RequestMapping(value="ProductDetail/{id}",method=RequestMethod.GET)
	public String searchId(@PathVariable("id")String id,ModelMap model){
		ProductTO productTO=new ProductTO();
		productTO=searchService.getProductById(id);
		model.addAttribute("productTO", productTO);
		return "ProductDetail";
	}
	/*Show the result of the search*/
	@RequestMapping(value="/selectShoe",method=RequestMethod.GET)
	public String searchForm(ModelMap model){
		String message="Image Got";
		System.out.println("In controller");
		ProductTO productTO=new ProductTO();
		model.addAttribute("productTO", productTO);
		return "SearchResult";
	}
	/*Search product by brand and price and category*/
	@RequestMapping(value="/search")
	public String searchParameter(@ModelAttribute ProductTO productTO,BindingResult result,ModelMap model){
		if(result.hasErrors())
			return "SearchResult";
		else{
			model.addAttribute("imageList", searchService.getParticularProduct(productTO.getProductBrand(),productTO.getProductPrice(),productTO.getProductCategory()));
			return "SearchResult"; 
		}
	}
	/*Men category products*/
	@RequestMapping (value="/MenProducts")
	public ModelAndView ShowMenProducts(){
		System.out.println("Men");
		return new ModelAndView("MenProducts", "imageList",searchService.getMenProduct());
	}
	/*Women category products*/
	@RequestMapping (value="/WomenProducts")
	public ModelAndView ShowWomenProducts(){
		System.out.println("Women");
		//ProductTO pTo = new ProductTO();
		return new ModelAndView("WomenProducts", "imageList",searchService.getWomenProduct());
	}
}


