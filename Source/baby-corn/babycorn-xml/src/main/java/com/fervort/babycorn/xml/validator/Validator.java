package com.fervort.babycorn.xml.validator;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to get result of the validation written using annotation BabyCornXMLvalidation.
 * <p>
 * Check documentation of individual field to know how to use it.
 *  
 * @author Nilesh Khaire
 *
 */
public class Validator {
	
	private Map<String,ValidationResult> validationList = new HashMap();
	
	/**
	 * Get collection of ValidationResult. 
	 * @return Collection of validation result. Key will be name of validation and value will be instance of ValidationResult.
	 */
	public Map<String,ValidationResult> getValidationList()
	{
		return validationList;
	}

	/**
	 * Get collection of ValidationResult based on severity passed as input parameters.
	 * 
	 * @param severity Severity for which you want ValidationResult. There are 2 possible severities ValidationResult.SEVERITY.STOP and ValidationResult.SEVERITY.CONTINUE
	 * @return Collection of validation result.
	 */
	public Map<String,ValidationResult> getValidationList(ValidationResult.SEVERITY severity)
	{
		Map<String,ValidationResult> localValidationList = new HashMap();
		
		for(Map.Entry<String, ValidationResult> entry: validationList.entrySet())
		{
			ValidationResult validationResult = entry.getValue();
			if(validationResult.getSeverity()==severity)
			{
				localValidationList.put(entry.getKey(), entry.getValue());
			}
		}
		return localValidationList;
	}
	
	/**
	 * Check if there are ValidationResult with severity STOP. 
	 * @return true if there is a ValidationResult with severity STOP. 
	 */
	public boolean hasSeverityStop()
	{
		return hasSeverity(ValidationResult.SEVERITY.STOP);
	}
	
	/**
	 * Check if there are ValidationResult with severity CONTINUE. 
	 * @return true if there is a ValidationResult with severity CONTINUE. 
	 */
	public boolean hasSeverityContinue()
	{
		return hasSeverity(ValidationResult.SEVERITY.CONTINUE);
	}
	
	/**
	 * Check if there are ValidationResult with severity passed as input parameter. 
	 * @param severity for which ValidationResult has to be checked.
	 * @return true if there is a ValidationResult with severity passed as input parameter.
	 */
	public boolean hasSeverity(ValidationResult.SEVERITY severity)
	{
		for(Map.Entry<String, ValidationResult> entry: validationList.entrySet())
		{
			ValidationResult validationResult = entry.getValue();
			if(validationResult.getSeverity()==severity)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get ValidationResult based on name of the java field.
	 * @param fieldName for which ValidationResult has to be checked. 
	 * @return ValidationResult
	 */
	public ValidationResult getValidationResult(String fieldName) 
	{
		return validationList.get(fieldName);
	}
	
	/**
	 * Note: This method is used internally.
	 * <p>
	 * Set validation result for field
	 * @param fieldName for which ValidationResult will be add
	 * @param validationResult ValidationResult for the field
	 */
	public void addValidationResult(String fieldName,ValidationResult validationResult) 
	{
		validationList.put(fieldName, validationResult);
	}
	
	// TODO Iterate map so key (field name) will also be display
	
	/**
	 * Return string representation of Validator class.
	 */
	@Override
	public String toString() {
		return "Validator [validationList=" + validationList + "]";
	}
	

}
