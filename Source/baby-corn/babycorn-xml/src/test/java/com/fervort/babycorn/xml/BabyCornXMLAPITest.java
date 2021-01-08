package com.fervort.babycorn.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BabyCornXMLAPITest {

	// Path src/test/resources/Students.xml
	String filePath = getClass().getResource("/Students.xml").getFile();
	
	@DisplayName("Test Students.xml")
	@Test 
	public void testAPIs() throws ParserConfigurationException, SAXException, IOException
	{
		BabyCornXML babyCornXML = new BabyCornXML(filePath);
		
		Document doc= babyCornXML.getDocumentRoot();
		
		NodeList nodeList = doc.getElementsByTagName("lastYearGrade");  
		
		Node node = nodeList.item(0);  
		System.out.println("lastYearGrade:" + node.getTextContent());
		
		// as string
		assertEquals("78.77", node.getTextContent());
		
	}
	
}
