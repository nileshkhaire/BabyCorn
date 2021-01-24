package com.fervort.babycorn.xml.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fervort.babycorn.xml.BabyCornXML;

public class BabyCornXMLValidatorTest {

	// Path src/test/resources/Students.xml
	String filePath = getClass().getResource("/Employees.xml").getFile();
	
	@DisplayName("Test Validation")
	@Test 
	void testValidation() throws Exception
	{
		//System.setProperty("babyCornXML.enableTraces","true");
		Employees employees = new Employees();
		BabyCornXML babyCornXML = new BabyCornXML(filePath,employees);
		
		System.out.println("Employee name "+employees.name);
		assertEquals(null,employees.name);
		
		System.out.println("Employee department "+employees.department);
		assertEquals("UNKNOWN_DEPARTMENT",employees.department);
		
		System.out.println("Employee bonus percentage "+employees.bonusPercentage);
		assertEquals(0.0F,employees.bonusPercentage);
		
		System.out.println("Employee salary group "+employees.salaryGroup);
		assertEquals('B',employees.salaryGroup);
		
		System.out.println("Employee ID "+employees.empID);
		assertEquals(0,employees.empID);
		
		System.out.println("Employee isInsured "+employees.isInsured);
		assertEquals(false,employees.isInsured);
		
		Map<String,String> projects = new HashMap();
		projects.put("prj347","Data Simulation");
		projects.put("prj204","Warehouse Automation");
		
		System.out.println("Employee projects "+employees.projects);
		assertEquals(projects,employees.projects);
		
		List<String> resources = new ArrayList();
		resources.add("Laptop");
		resources.add("Car");
		resources.add("Phone");
		System.out.println("Employee resources "+employees.resources);
		assertEquals(resources,employees.resources);

	}
}
