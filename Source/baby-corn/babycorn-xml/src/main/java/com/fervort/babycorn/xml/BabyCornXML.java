package com.fervort.babycorn.xml;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fervort.babycorn.xml.annotation.BabyCornXMLField;
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
			Annotation annotation = field.getAnnotation(BabyCornXMLField.class);
			if(annotation instanceof BabyCornXMLField)
			{
				printTraces("Annotation: "+annotation);
				BabyCornXMLField babyCornXMLField =(BabyCornXMLField)annotation;
				printTraces("xPath: "+babyCornXMLField.xPath()+" Name: "+babyCornXMLField.name());
				
				if(field.getType().equals(String.class)) {
					processStringField(field,babyCornXMLField);
				}else if(field.getType().equals(double.class)) {
					processDoubleField(field,babyCornXMLField);
				
				}else if(field.getType().equals(Map.class) || field.getType().equals(HashMap.class)) {
					processMapField(field,babyCornXMLField);
				}
				else if(field.getType().equals(List.class) || field.getType().equals(ArrayList.class)) {
					processListField(field,babyCornXMLField);
				}
			}
		}
	}
	
	private void processStringField(Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		String stringValue = this.babyCornXMLReader.evaluateXPathToString(babyCornXMLField.xPath());
		printTraces("Setting string on "+currentField.getName()+" Value: "+stringValue);
		currentField.set(object, stringValue);
	}
	private void processDoubleField(Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		Double doubleValue = this.babyCornXMLReader.evaluateXPathToDouble(babyCornXMLField.xPath());
		printTraces("Setting string on "+currentField.getName()+" Value: "+doubleValue);
		currentField.set(object, doubleValue);
	}
	
	@SuppressWarnings("unchecked")
	private void processMapField(Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		NodeList nodeList = this.babyCornXMLReader.evaluateXPathToNodeList(babyCornXMLField.xPath());
		
		Map map = new HashMap();
		String key = babyCornXMLField.mapKey();
		String value = babyCornXMLField.mapValue();
		String keyValue ="";
		String valueValue="";
		for(int i = 0; i < nodeList.getLength(); i++) {                
                
			Node node = nodeList.item(i);
			String textContent =node.getTextContent();
			keyValue = key.trim().equalsIgnoreCase("text()")?textContent:"";
			valueValue = value.trim().equalsIgnoreCase("text()")?textContent:"";
			
            if (node.hasAttributes()) {
            	
            	if(key.trim().startsWith("@"))
            	{
            		Attr attr = (Attr) node.getAttributes().getNamedItem(key.replace("@", ""));
                    if (attr != null) {
                    	keyValue = attr.getValue();   
                    }
                }
            	if(value.trim().startsWith("@"))
            	{
            		Attr attr = (Attr) node.getAttributes().getNamedItem(value.replace("@", ""));
                    if (attr != null) {
                    	valueValue= attr.getValue();   
                    }
                }
            }
            printTraces("Key "+keyValue+" : Value "+valueValue);
            map.put(keyValue, valueValue);
            
		}
		currentField.set(object, map);
	}
	
	@SuppressWarnings("unchecked")
	private void processListField(Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		NodeList nodeList = this.babyCornXMLReader.evaluateXPathToNodeList(babyCornXMLField.xPath());
		
		List list = new ArrayList();
		String value = babyCornXMLField.listValue();
		
		String valueValue="";
		for(int i = 0; i < nodeList.getLength(); i++) {                
                
			Node node = nodeList.item(i);
			String textContent =node.getTextContent();
			valueValue = value.trim().equalsIgnoreCase("text()")?textContent:"";
			
            if (node.hasAttributes()) {
            	
            	if(value.trim().startsWith("@"))
            	{
            		Attr attr = (Attr) node.getAttributes().getNamedItem(value.replace("@", ""));
                    if (attr != null) {
                    	valueValue = attr.getValue();   
                    }
                }
            }
            printTraces("Value "+valueValue);
            list.add(valueValue);
            
		}
		currentField.set(object, list);
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
