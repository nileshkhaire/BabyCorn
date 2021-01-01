# BabyCorn XML
Short and sweet strongly typed XML reader for JVM.

BabyCorn XML is a library to read XML data into Java data types using XPath selector. 

## How to use : 

In 3 quick steps: 

1. Download latest library `babycorn-xml-***.jar` and add as a dependency.
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
		
		BabyCornXML babyCornXML = new BabyCornXML("D:\\Nilesh\\Work\\github\\BabyCorn\\repo\\BabyCorn\\Source\\baby-corn\\babycorn-xml\\src\\test\\resources\\Students.xml",students);
		
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
