package com.hcl.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/*
 * This is the javadoc comment for the interface. 
 * This interface  for phone validation
 * @version 1.01 22/03/2016
*/
@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
	
	String message() default "{Phone}";
    
    Class<?>[] groups() default {};
      
    Class<? extends Payload>[] payload() default {};
       

}