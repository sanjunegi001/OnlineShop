package com.hcl.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hcl.model.CustomerTo;;
/*
 * This is the javadoc comment for the class. 
 * This class  forpassword validation
 * @version 1.01 22/03/2016
*/
public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals,Object>{

	@Override
	public void initialize(PasswordEquals arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
		CustomerTo customerTo=(CustomerTo) candidate;
		if(customerTo.getPassword().equals(customerTo.getConfirmPassword())){
			return true;
		}else{
			return false;
		}
	}
	
	

}
