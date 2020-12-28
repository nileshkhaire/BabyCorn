package com.fervort.babycorn.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fervort.babycorn.xml.annotation.BabyCornXMLField;

public class Students {

	@BabyCornXMLField(xPath = "students/student/name")
	String name;

	@BabyCornXMLField(xPath = "students/student/rollno")
	int rollNumber;
	
	@BabyCornXMLField(xPath = "students/student/division")
	char division;
	
	@BabyCornXMLField(xPath = "students/student/lastYearGrade")
	float lastYearGradeofStudent;
	
	@BabyCornXMLField(xPath = "students/student/inTopTen")
	boolean isStudentInTop10;
	
	@BabyCornXMLField(xPath = "students/student/secretKeyNumber")
	long secretKeyNumber;
	
	@BabyCornXMLField(xPath = "students/student/studentWeight")
	double weight;
	
	@BabyCornXMLField(xPath = "students/student/hobbies/hobby" ,listValue = "text()")
	List hobbies = new ArrayList();
	
	@BabyCornXMLField(xPath = "students/student/subjects/subject" ,mapKey = "@name",mapValue = "@teacherName")
	Map<String,String> subjects = new HashMap();
}
