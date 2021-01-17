package com.fervort.babycorn.xml.validator;

import java.util.HashMap;
import java.util.Map;

public class Validator {
	
	private Map<String,ValidationResult> validationList = new HashMap();
	
	public Map<String,ValidationResult> getValidationList()
	{
		return validationList;
	}

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
	
	public boolean hasSeverityStop()
	{
		return hasSeverity(ValidationResult.SEVERITY.STOP);
	}
	
	public boolean hasSeverityContinue()
	{
		return hasSeverity(ValidationResult.SEVERITY.CONTINUE);
	}
	
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
	
	public void addValidationResult(String fieldName,ValidationResult validationResult) 
	{
		validationList.put(fieldName, validationResult);
	}
	
	// TODO Iterate map so key (field name) will also be display
	@Override
	public String toString() {
		return "Validator [validationList=" + validationList + "]";
	}
	

}
