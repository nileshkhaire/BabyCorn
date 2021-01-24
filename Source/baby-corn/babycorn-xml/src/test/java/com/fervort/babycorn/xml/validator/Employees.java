package com.fervort.babycorn.xml.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fervort.babycorn.xml.annotation.BabyCornXMLField;

public class Employees {

	@BabyCornXMLField(xPath = "employees/employee/name" , validationMethod ="validateName")
	public String name;
	
	@BabyCornXMLField(xPath = "employees/employee/department",validationMethod = "validateDepartment")
	public String department;
	
	@BabyCornXMLField(xPath = "employees/employee/BonusPercentage", validationMethod = "validateBonusPercentage")
	public Float bonusPercentage;
	
	@BabyCornXMLField(xPath = "employees/employee/SalaryGroup", validationMethod = "validateSalaryGroup")
	public char salaryGroup;
	
	@BabyCornXMLField(xPath = "employees/employee/id", validationMethod = "validateEmployeeID")
	public int empID;
	
	@BabyCornXMLField(xPath = "employees/employee/IsInsured", validationMethod = "validateIsInsuredField")
	public boolean isInsured;
	
	@BabyCornXMLField(xPath = "employees/employee/projects/project", mapKey = "@id",mapValue = "text()", validationMethod ="validateProjects")
	public Map<String,String> projects = new HashMap();

	@BabyCornXMLField(xPath = "employees/employee/resources/resource", listValue = "text()",validationMethod = "validateResources")
	public List<String> resources = new ArrayList();
	
	public ValidationResult validateName(Field field, Object object)
	{
		String nameFromXML = (String)object;
		ValidationResult result = new ValidationResult(true);
		if(nameFromXML.trim().isEmpty())
		{
			result = new ValidationResult(false);
			result.setIfInvalidMessage("Name is XML is empty");
			result.setSeverity(ValidationResult.SEVERITY.STOP);
			
		}
		return result;
	}
	
	public ValidationResult validateBonusPercentage(Field field, Object object)
	{
		Double bonusPercentageFromXML = (Double)object;
		float bonusPercentage = bonusPercentageFromXML.floatValue(); 
		ValidationResult result = new ValidationResult(true);
		if(bonusPercentage>100 || bonusPercentage<0)
		{
			result = new ValidationResult(false);
			result.setIfInvalidMessage(bonusPercentage+" is not a valid percentage");
			result.setSeverity(ValidationResult.SEVERITY.CONTINUE);
			float ifInvalidValue=  (float) 00.00;
			result.setIfInvalidValue(ifInvalidValue);
		}
		return result;
	}
	
	public ValidationResult validateDepartment(Field field, Object object)
	{
		String[] departments = {"IT","Finance","Manufacturing"};
		
		String departmentFromXML = (String)object;
		ValidationResult result = new ValidationResult(true);
		if(!Arrays.asList(departments).contains(departmentFromXML))
		{
			result = new ValidationResult(false);
			result.setIfInvalidValue("UNKNOWN_DEPARTMENT");
		}
		return result;
	} 
	
	public ValidationResult validateSalaryGroup(Field field, Object object)
	{
		char salaryGroupFromXML = object.toString().charAt(0);
		ValidationResult result = new ValidationResult(true);
		if(!Character.isUpperCase(salaryGroupFromXML))
		{
			result = new ValidationResult(false);
			result.setIfInvalidValue(Character.toUpperCase(salaryGroupFromXML));
		}
		return result;
	} 
	
	public ValidationResult validateEmployeeID(Field field, Object object)
	{
		Double bonusPercentageFromXML = (Double)object;
		int empID = bonusPercentageFromXML.intValue(); 
		ValidationResult result = new ValidationResult(true);
		if(empID<100)
		{
			result = new ValidationResult(false);
			result.setIfInvalidMessage(empID+" is not a valid");
			int ifInvalidValue=  00;
			result.setIfInvalidValue(ifInvalidValue);
		}
		return result;
	}
	public ValidationResult validateIsInsuredField(Field field, Object object)
	{
		String isInsuredFromXML = (String)object;
		ValidationResult result = new ValidationResult(true);
		if(isNumeric(isInsuredFromXML))
		{
			boolean returnValue = true;
			int parsedValue = Integer.parseInt(isInsuredFromXML);
			if(parsedValue<0)
				returnValue= false;
				
			result = new ValidationResult(false);
			result.setIfInvalidValue(returnValue);
		}
		return result;
	}
	
	private boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	
	public ValidationResult validateProjects(Field field, Object object)
	{
		boolean isSecureProjectRemove = false;
		Map<String,String> projectsFromXML = (Map)object;
		Map<String,String> tempMap = new HashMap();
		tempMap.putAll(projectsFromXML);
		for (Map.Entry<String,String> entry : projectsFromXML.entrySet()) {
			
			String key =entry.getKey();
			if(key.startsWith("srp"))
			{
				tempMap.remove(key);
				isSecureProjectRemove= true;
			}
		}

		ValidationResult result = new ValidationResult(true);
		
		if(isSecureProjectRemove)
		{
			result = new ValidationResult(false);
			result.setIfInvalidValue(tempMap);
		}
		return result;
	}

	public ValidationResult validateResources(Field field, Object object)
	{
		boolean isListUpdated = false;
		List<String> resourcesFromXML = (List)object;
		
		if(resourcesFromXML.contains("Cell Phone"))
		{
			resourcesFromXML.remove("Cell Phone");
			resourcesFromXML.add("Phone");
		}

		ValidationResult result = new ValidationResult(true);
		
		if(isListUpdated)
		{
			result = new ValidationResult(false);
			result.setIfInvalidValue(resourcesFromXML);
		}
		return result;
	}
	
}

