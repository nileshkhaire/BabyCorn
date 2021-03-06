[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.fervort.babycorn/babycorn-xml/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.fervort.babycorn/babycorn-xml)

# BabyCorn XML
Short and sweet strongly typed XML reader for JVM.

BabyCorn XML is a library to read XML data into Java data types using XPath selector. 

It could be a good configuration library because of in-built features like validator and preprocessor. 

## Adding BabyCorn XML to your build:

There are multiple options. Select one of the following:

### Download from GitHub releases:

[GitHub Releases](https://github.com/nileshkhaire/BabyCorn/releases)

### To add a dependency using Maven, use the following:

```xml
<dependency>
  <groupId>com.fervort.babycorn</groupId>
  <artifactId>babycorn-xml</artifactId>
  <version>1.0.0</version>
</dependency>
```

### To add a dependency using Gradle:

```gradle
implementation 'com.fervort.babycorn:babycorn-xml:1.0.0'
```

## How to use : 

In 3 quick steps: 

1. Add BabyCorn XML jar to your build as specified in above options.
2. Create a Java class with fields and annotate it using XPath as per your XML file.
3. Create an object of a java field class you have created in the last step and pass it to BabyCornXML constructor. All values will be set to field class you have created. That's All!

## Example :

### Your XML :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<students>
	<student>
		<name>Foo Bar</name>
		<rollno>11</rollno>
		<division>A</division>
		<lastYearGrade>78.77</lastYearGrade>
		<inTopTen>yes</inTopTen>
		<secretKeyNumber>922337203685477</secretKeyNumber>
		<studentWeight>63.9873333</studentWeight>
		<address postcode="900111" city="California"/>
		<hobbies>
			<hobby>Singing</hobby>
			<hobby>Reading</hobby>
			<hobby>Yoga</hobby>
		</hobbies>
		<subjects>
			<subject name="Science" teacherName="Abc Pqr"/>
			<subject name="Mathematics" teacherName="Lmn Opq"/>
			<subject name="English" teacherName="Def Xyz"/>
		</subjects>
		<projects>
			<project id="101">Painting</project>
			<project id="102">calligraphy</project>
		</projects>
	</student>
</students>	
```

### Create a Java class with fields and annotate it using XPath :

```java
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
```

### Create an object of a java field class you have created in the last step and pass it to BabyCornXML constructor :

```java

public class StudentTest {

	public static void main(String[] args) throws Exception {
		
		Students students = new Students();
		
		BabyCornXML babyCornXML = new BabyCornXML("Students.xml",students);
		
		System.out.println("Name: "+students.name);
		
		System.out.println("Roll Number "+students.rollNumber);
		
		System.out.println("Division "+students.division);
		
		System.out.println("In Top 10 "+students.isStudentInTop10);
		
		System.out.println("last year grade "+students.lastYearGradeofStudent);
		
		System.out.println("Weight "+students.weight);
	
		System.out.println("SecretKey "+students.secretKeyNumber);

		System.out.println("Hobbies "+students.hobbies);
		
		System.out.println("Subjects "+students.subjects);

	}

}


```

### Output :

```bash
Name: Foo Bar
Roll Number 11
Division A
In Top 10 true
last year grade 78.77
Weight 63.9873333
SecretKey 922337203685477
Hobbies [Singing, Reading, Yoga]
Subjects {English=Def Xyz, Science=Abc Pqr, Mathematics=Lmn Opq}
```

## Handling multiple nodes :
If there are multiple nodes/records in the XML, you can create 2 classes, 1 for holding list of records and other one for holding fields. For example: 

### XML with multiple nodes:
```xml
<teachers>
	<teacher>
		<name>Abc</name>
		<tid>101</tid>
		<department>Art</department>
	</teacher>
	<teacher>
		<name>Pqr</name>
		<tid>102</tid>
		<department>Science</department>
	</teacher>
	<teacher>
		<name>Xyz</name>
		<tid>103</tid>
		<department>Commerce</department>
	</teacher>
</teachers>
```

### Class to hold list of teachers:
```java
public class Teachers {

	// Note Xpath, it is teachers/teacher
	@BabyCornXMLField(xPath = "teachers/teacher")
	List<Teacher> teacherList = new ArrayList<Teacher>();
}
```
### Class to hold teacher fields:
```java
public class Teacher {

	// Note Xpath, it is only name, excluding teachers/teacher
	@BabyCornXMLField(xPath = "name")
	public String name;
	
	@BabyCornXMLField(xPath = "tid")
	public int tid;
	
	@BabyCornXMLField(xPath = "department")
	public String department;
}
```
### Get list of teachers and fields:
```java
public static void main(String[] args) throws Exception {

	Teachers teachers = new Teachers();
	BabyCornXML babyCornXML = new BabyCornXML("Teachers.xml",teachers);
	
	// Iterate through each teacher
	for(Teacher teacher: teachers.teacherList)
	{
		System.out.println("name: "+teacher.name);
		System.out.println("teacher id: "+teacher.tid);
		System.out.println("department: "+teacher.department);
		System.out.println("");
	}
```
### Output :

```bash
name: Abc
teacher id: 101
department: Art

name: Pqr
teacher id: 102
department: Science

name: Xyz
teacher id: 103
department: Commerce
```

## Validator & Pre processor :
Anything can be written in a XML file that can cause a program failure. To avoid such scenarios, validator can be used. You can write your own validator or use built in validators.

### Writing custom validator:
You can write your own validators. Here are simple steps:
1. Add `@BabyCornXMLValidation(validationMethod = "validateEmployeeName")` annotation on the field. Here `validateEmployeeName` is a java method, where validation code will be written.
```java
public class Employees {
	
	@BabyCornXMLValidation(validationMethod = "validateEmployeeName")
	@BabyCornXMLField(xPath = "employees/employee/name")
	public String name;

}

```
2. Write a validation method `validateEmployeeName` in class Emplyees. Method should have 2 parameters as written below.
```java
public ValidationResult validateEmployeeName(Field field, Object object)
{
	String nameFromXML = (String)object;
	ValidationResult result = new ValidationResult(true);
	if(nameFromXML.trim().isEmpty())
	{
		result = new ValidationResult(false);
		result.setIfInvalidMessage("Name in XML is empty");
		result.setSeverity(ValidationResult.SEVERITY.STOP);
		// You can use SEVERITY.CONTINUE to continue with the execution
		//result.setSeverity(ValidationResult.SEVERITY.CONTINUE);
		
	}
	return result;
}
```
3. You can get result of validation using BabyCorn XML APIs
```java
	Employees employees = new Employees();
	BabyCornXML babyCornXML = new BabyCornXML("Employees.xml",employees);
	Validator validator =babyCornXML.getValidator();
	validator.getValidationList();
	System.out.println("validation list "+validator);
```
Check complete example here [Employees.java](https://github.com/nileshkhaire/BabyCorn/blob/main/Source/baby-corn/babycorn-xml/src/test/java/com/fervort/babycorn/xml/validator/Employees.java)

> Note : For field type `String,Char,Boolean` 2nd method parameter `object` could be cast to `String` and for `Double,float,Long` parameters `object` could be cast to `Double` before using. Return value `setIfInvalidValue(returnValue)` should be same as type of the field.

### Validator to pre process the field values:
Validator can be used to pre process the value. For example: In Employees.java example, if department is not a `IT,Finance or Manufacturing` then `UNKNOWN_DEPARTMENT` will be set on the field.
```java
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
```
## BabyCorn XML APIs:
BabyCorn XML can also be used as XML DOM APIs

```java
public void testAPIs() throws Exception
{
	BabyCornXML babyCornXML = new BabyCornXML("D:\Nilesh\Students.xml");
		
	Document doc= babyCornXML.getDocumentRoot();
		
	NodeList nodeList = doc.getElementsByTagName("lastYearGrade");  
		
	Node node = nodeList.item(0);  
	
	System.out.println("lastYearGrade:" + node.getTextContent());
} 
```
