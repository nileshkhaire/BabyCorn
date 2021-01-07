package com.fervort.babycorn.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

public class BabyCornXMLStudentTest {

	// Path src/test/resources/Students.xml
	String filePath = getClass().getResource("/Students.xml").getFile();
	
	@DisplayName("Test Students.xml")
	@Test 
	void testBabyCornXML() throws IllegalArgumentException, IllegalAccessException, XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		
		
		//System.out.println("file path "+filePath);
		
		Students students = new Students();
		BabyCornXML babyCornXML = new BabyCornXML(filePath,students);
		
		System.out.println("Testing: "+students.name);
		
		assertEquals("Foo Bar",students.name);
		
		System.out.println("Roll Number "+students.rollNumber);
		assertEquals(11,students.rollNumber);
		
		System.out.println("Division "+students.division);
		assertEquals('A',students.division);
		
		System.out.println("In Top 10 "+students.isStudentInTop10);
		assertEquals(students.isStudentInTop10, true);
		
		System.out.println("last year grade "+students.lastYearGradeofStudent);
		assertEquals(78.77F,students.lastYearGradeofStudent);
		
		System.out.println("Weight "+students.weight);
		assertEquals(63.9873333,students.weight);
	
		System.out.println("SecretKey "+students.secretKeyNumber);
		assertEquals(922337203685477L,students.secretKeyNumber); // append L 

		System.out.println("Hobbies "+students.hobbies);
		
		List<String> hobbies = new ArrayList();
		hobbies.add("Singing");
		hobbies.add("Reading");
		hobbies.add("Yoga");
		
		assertEquals(hobbies, students.hobbies);
		
		System.out.println("Subjects "+students.subjects);
		
		Map<String,String> subjects = new HashMap();
		subjects.put("Science","Abc Pqr");
		subjects.put("Mathematics","Lmn Opq");
		subjects.put("English","Def Xyz");
		
		assertEquals(subjects, students.subjects);
		
		System.out.println("===Static test===");
		
		System.out.println("Static name "+Students.nameStatic);
		
		assertEquals("Foo Bar", Students.nameStatic);
	}
}
