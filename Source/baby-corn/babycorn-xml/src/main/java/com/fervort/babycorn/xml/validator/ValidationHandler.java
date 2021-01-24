/***

Copyright [2021] [Nilesh Khaire]

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

package com.fervort.babycorn.xml.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.fervort.babycorn.xml.annotation.BabyCornXMLField;

public class ValidationHandler {

	private Validator validator=null;
	
	public ValidationHandler()
	{
		this.validator= new Validator();
	}
	
	public Validator getValidator()
	{
		return this.validator;
	}
	
	// TODO: Should we throw exception, instead of catching here ? 
	public ValidationResult handleFieldValidation(Object object,Field field,Object currentFieldValue) //throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Annotation annotation = field.getAnnotation(BabyCornXMLField.class);
		if(annotation instanceof BabyCornXMLField)
		{
			BabyCornXMLField babyCornXMLField =(BabyCornXMLField)annotation;
			String validateMethod = babyCornXMLField.validationMethod();
			if(!validateMethod.equals("[unassigned]"))
			{
				@SuppressWarnings("rawtypes")
				Class clazz = object.getClass();
				
				try
				{
					@SuppressWarnings("unchecked")
					Method method = clazz.getMethod(validateMethod, Field.class,Object.class);
					ValidationResult validationResult = (ValidationResult) method.invoke(object, field,currentFieldValue);
					if(validationResult.getFieldName()==null)
					{
						validationResult.setFieldName(field.getName());
					}
					validator.addValidationResult(field.getName(), validationResult);
					return validationResult;
				}
				catch(Exception ex)
				{
					System.err.println("Error: "+ex);
					ex.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
