/***

Copyright [2020] [Nilesh Khaire]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package com.fervort.babycorn.xml.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to define XPath of the XML on the java fields.
 * For List and Map parameters like mapKey, mapValue,listValue can be used. 
 * 
 * <p>
 * Check documentation of individual fields to know more.
 * 
 * @author Nilesh Khaire
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BabyCornXMLField {

	/**
	 * XPath of XML
	 * @return xPath of XML
	 */
	String xPath();
	
	/**
	 * Used to define what value will be used as Map/HashMap key for current node.
	 * 
	 * Valid values are
	 * <p>
	 * &#64;attributename : Here attributename is name of XML attribute present on current XML node.
	 * Value of this attribute will be used as key of Map/HashMap.
	 * <p>
	 * text() : XML text content will be used as a Map/HashMap key. 
	 * 
	 * <p>
	 * Note:This annotation can only be used on field of type Map and HashMap. 
	 * @return mapKey
	 */
	String mapKey() default "[unassigned]";
	
	/**
	 * Used to define what value will be used as Map/HashMap value for current node.
	 * 
	 * Valid values are
	 * <p>
	 * &#64;attributename : Here attributename is name of XML attribute present on current XML node.
	 * Value of this attribute will be used as value of Map/HashMap.
	 * <p>
	 * text() : XML text content will be used as a Map/HashMap value. 
	 * 
	 * <p>
	 * Note:This annotation can only be used on field of type Map and HashMap. 
	 * @return mapValue
	 */
	String mapValue() default "[unassigned]";
	
	/**
	 * Used to define what value will be used as List/ArrayList content.
	 * 
	 * Valid values are
	 * <p>
	 * &#64;attributename : Here attributename is name of XML attribute present on current XML node.
	 * Value of this attribute will be used as content of List/ArrayList.
	 * <p>
	 * text() : XML text content will be used as a list value. 
	 * 
	 * <p>
	 * Note:This annotation can only be used on field of type List/ArrayList . 
	 * @return listValue
	 */
	String listValue() default "[unassigned]";
	
	/**
	 * Used to define name for the field.
	 * <p>This is optional field
	 * Note: It is not used internally.
	 * @return name
	 */
    String name() default "[unassigned]";
}
