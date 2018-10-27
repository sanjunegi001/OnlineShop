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
 * This interface  for validation
 * @version 1.01 22/03/2016
*/
@Documented
@Constraint(validatedBy = PasswordEqualsValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordEquals {
	
String message() default "{PasswordEquals}";

    
    Class<?>[] groups() default {};
      
    Class<? extends Payload>[] payload() default {};

}
