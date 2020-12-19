package com.fervort.babycorn.xml;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.fervort.babycorn.xml.annotation.BabyCornXMLAnnotation;
import com.fervort.babycorn.xml.reader.BabyCornXMLReader;
import com.fervort.babycorn.xml.reader.BabyCornXMLReaderFactory;
import com.fervort.babycorn.xml.reader.BabyCornXMLReaderFactory.FactoryType;

public class BabyCornXML {
	
	private String xmlPath;
	private Object object;
	private BabyCornXMLReader babyCornXMLReader;
	private boolean isTracesEnabled = false;
	
	public BabyCornXML(String xmlPath,Object object) throws IllegalArgumentException, IllegalAccessException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		this.xmlPath= xmlPath;
		this.object= object;
		this.babyCornXMLReader = BabyCornXMLReaderFactory.getXMLReader(FactoryType.DEFAULT);
		this.babyCornXMLReader.initParser(xmlPath);
		this.babyCornXMLReader.initXPath();
		buildAnnotatedObject();
	}
	
	public BabyCornXML(String xmlPath,Object object,boolean enableTraces) throws IllegalArgumentException, IllegalAccessException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		isTracesEnabled=enableTraces;
		this.xmlPath= xmlPath;
		this.object= object;
		this.babyCornXMLReader = BabyCornXMLReaderFactory.getXMLReader(FactoryType.DEFAULT);
		this.babyCornXMLReader.initParser(xmlPath);
		this.babyCornXMLReader.initXPath();
		buildAnnotatedObject();
	}
	
	public void buildAnnotatedObject() throws IllegalArgumentException, IllegalAccessException, XPathExpressionException
	{
		printTraces("Building annotated object: "+object.getClass());
		Field[] fields = object.getClass().getDeclaredFields();
		for(Field field: fields)
		{
			printTraces("Processing field: "+field.getName()+" Type: "+field.getType());
			Annotation annotation = field.getAnnotation(BabyCornXMLAnnotation.class);
			if(annotation instanceof BabyCornXMLAnnotation)
			{
				printTraces("Annotation: "+annotation);
				BabyCornXMLAnnotation babyCornXMLAnnotation =(BabyCornXMLAnnotation)annotation;
				printTraces("xPath: "+babyCornXMLAnnotation.xPath()+" Name: "+babyCornXMLAnnotation.name());
				
				if(field.getType().equals(String.class)) {
					String stringValue = this.babyCornXMLReader.evaluateXPathToString(babyCornXMLAnnotation.xPath());
					printTraces("Setting string on "+field.getName()+" Value: "+stringValue);
					field.set(object, stringValue);
					
				}else if(field.getType().equals(double.class)) {
					Double doubleValue = this.babyCornXMLReader.evaluateXPathToDouble(babyCornXMLAnnotation.xPath());
					printTraces("Setting string on "+field.getName()+" Value: "+doubleValue);
					field.set(object, doubleValue);
				}
			}
		}
	}
	
	public void enableTraces()
	{
		this.isTracesEnabled= true;
	}

	void printTraces(String string)
	{
		if(isTracesEnabled)
			System.out.println("TRACE: "+string);
	}

}
