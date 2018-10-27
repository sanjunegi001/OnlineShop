package com.hcl.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/*
 * This is the javadoc comment for the class. 
 * This class  for phone validation
 * @version 1.01 22/03/2016
*/
public class PhoneValidator implements ConstraintValidator<Phone,String>{

	@Override
	public void initialize(Phone arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext arg1) {
		if(phoneNo==null){
		return false;
		}
		//validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")){
        	return true;
        }else{
        	return false;
        }
        
	}
	
	

}
