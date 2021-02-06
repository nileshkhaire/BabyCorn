package com.fervort.babycorn.xml.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This class is used to define validation on the field.
 * <p>This validation will be used only when another validation BabyCornXMLField is used on the same field.
 * 
 * @author Nilesh Khaire
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BabyCornXMLValidation {

	/**
	 * Name of java method/function which is written in the same class where fields are defined.
	 * <p>
	 * Method shall have 2 parameters. 
	 * <p>1st parameter is of type java.lang.reflect.Field. This parameter will give you access to current field.
	 * <p>2nd parameter is of type java.lang.Object . This parameter will give you access to value of the field. You can cast the values before using it.
	 *	@return validation Method 
	 */
    String validationMethod() default "[unassigned]";
}
