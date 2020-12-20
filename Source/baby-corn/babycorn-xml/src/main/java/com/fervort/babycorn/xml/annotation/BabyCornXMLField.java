package com.fervort.babycorn.xml.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BabyCornXMLField {

	String xPath();
	String mapKey() default "[unassigned]";
	String mapValue() default "[unassigned]";
	String listValue() default "[unassigned]";
    String name() default "[unassigned]";
}
