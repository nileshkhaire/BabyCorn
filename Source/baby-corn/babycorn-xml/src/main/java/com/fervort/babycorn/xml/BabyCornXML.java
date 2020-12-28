/***

Copyright [2020] [Nilesh Khaire]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package com.fervort.babycorn.xml;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fervort.babycorn.xml.annotation.BabyCornXMLField;
import com.fervort.babycorn.xml.reader.BabyCornXMLReader;
import com.fervort.babycorn.xml.reader.BabyCornXMLReaderFactory;
import com.fervort.babycorn.xml.reader.BabyCornXMLReaderFactory.FactoryType;

public class BabyCornXML {
	
	private String xmlPath;
	private BabyCornXMLReader babyCornXMLReader;
	private boolean isTracesEnabled = false;
	
	public BabyCornXML(String xmlPath,Object object) throws IllegalArgumentException, IllegalAccessException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		this.xmlPath= xmlPath;
		this.babyCornXMLReader = BabyCornXMLReaderFactory.getXMLReader(FactoryType.DEFAULT);
		this.babyCornXMLReader.initParser(xmlPath);
		this.babyCornXMLReader.initXPath();
		buildAnnotatedObject(object);
	}
	
	public BabyCornXML(String xmlPath,Object object,boolean enableTraces) throws IllegalArgumentException, IllegalAccessException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		isTracesEnabled=enableTraces;
		this.xmlPath= xmlPath;
		this.babyCornXMLReader = BabyCornXMLReaderFactory.getXMLReader(FactoryType.DEFAULT);
		this.babyCornXMLReader.initParser(xmlPath);
		this.babyCornXMLReader.initXPath();
		buildAnnotatedObject(object);
	}
	public void buildAnnotatedObject(Object object) throws IllegalArgumentException, IllegalAccessException, XPathExpressionException
	{
		buildAnnotatedObject(this.babyCornXMLReader.getDocumentRoot(),object);
	}
	public void buildAnnotatedObject(Node node,Object object) throws IllegalArgumentException, IllegalAccessException, XPathExpressionException
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
					processStringField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
					processIntegerField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(double.class) || field.getType().equals(Double.class)) {
					processDoubleField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
					processBooleanField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(char.class) || field.getType().equals(Character.class)) {
					processCharacterField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(short.class) || field.getType().equals(Short.class)) {
					processShortField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(long.class) || field.getType().equals(Long.class)) {
					processLongField(node,object,field,babyCornXMLField);
				}else if(field.getType().equals(float.class) || field.getType().equals(Float.class)) {
					processFloatField(node,object,field,babyCornXMLField);
				}
				/*else if(field.getType().equals(byte.class) || field.getType().equals(Byte.class)) {
					//processIntegerField(node,object,field,babyCornXMLField);
				
				}*/
				else if(field.getType().equals(Map.class) || field.getType().equals(HashMap.class)) {
					processMapField(node,object,field,babyCornXMLField);
				}
				else if(field.getType().equals(List.class) || field.getType().equals(ArrayList.class)) {
					
					if(isListOfObjects(field))
					{
						processListOfObjectsField(node,object,field,babyCornXMLField);
					}
					else
					{
						processListField(node,object,field,babyCornXMLField);
					}
				}else if(!isBasicType(field.getType()))
				{
					processObjectField(node,object,field,babyCornXMLField);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processListOfObjectsField(Node node,Object object, Field currentField, BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		
		NodeList nodeList = this.babyCornXMLReader.evaluateXPathToNodeList(node,babyCornXMLField.xPath());
		Object listObject= currentField.get(object);
		printTraces("Procesing List of object field on "+listObject.getClass()+" Node name: "+node.getNodeName()+" XPath: "+babyCornXMLField.xPath());
		Type[] types = getFieldParameterizedType(currentField);
		if(types!=null)
		{
			printTraces("ParameterizedType Found "+types[0]);
			Class<?> clazz =(Class<?>) types[0];
			
			for(int i=0;i<nodeList.getLength();i++)
			{
				Object subObject;
				try {
					subObject = clazz.getConstructor().newInstance();
					Node subNode =nodeList.item(i);
					buildAnnotatedObject(subNode,subObject);
					
					printTraces("Created Object of "+subObject);
					
					if(clazz.getMethod("toString").getDeclaringClass().equals(Object.class))
					{
						printTraces("You can implement toString() method to see all set fields of the class "+clazz); 
					}
					
					@SuppressWarnings("rawtypes")
					List list =(List)listObject;
					list.add(subObject);
					printTraces("Added object in the list");
					
				} catch (InstantiationException | NoSuchMethodException | SecurityException e) {
					printTraces("InstantiationException : "+e.getLocalizedMessage());
				} catch (InvocationTargetException e) {
					printTraces("InvocationTargetException : "+e.getLocalizedMessage());
				}
			}
		}
			
	}

	private void processObjectField(Node node,Object object, Field currentField, BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		
		Node subNode = this.babyCornXMLReader.evaluateXPathToNode(node,babyCornXMLField.xPath());
		Object subObject= currentField.get(object);
		if(subObject!=null)
		{
			printTraces("Procesing object field on "+subObject.getClass()+" Node name: "+subNode.getNodeName()+" XPath: "+babyCornXMLField.xPath());
			buildAnnotatedObject(subNode,subObject);
		}
	}
	private void processStringField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		String stringValue = this.babyCornXMLReader.evaluateXPathToString(node,babyCornXMLField.xPath());
		printTraces("Setting string on "+currentField.getName()+" Value: "+stringValue);
		currentField.set(object, stringValue);
	}
	private void processDoubleField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		Double doubleValue = this.babyCornXMLReader.evaluateXPathToNumber(node,babyCornXMLField.xPath());
		printTraces("Setting double on "+currentField.getName()+" Value: "+doubleValue);
		if(!doubleValue.isNaN())
		{
			currentField.set(object, doubleValue);
		}
	}
	private void processIntegerField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		Double doubleValue = this.babyCornXMLReader.evaluateXPathToNumber(node,babyCornXMLField.xPath());
		printTraces("Setting integer on "+currentField.getName()+" Value: "+doubleValue);
		if(!doubleValue.isNaN())
		{
			int intValue = doubleValue.intValue();
			currentField.set(object, intValue);
		}
	}
	private void processShortField(Node node, Object object, Field currentField, BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		Double doubleValue = this.babyCornXMLReader.evaluateXPathToNumber(node,babyCornXMLField.xPath());
		printTraces("Setting short on "+currentField.getName()+" Value: "+doubleValue);
		if(!doubleValue.isNaN())
		{
			short shortValue = doubleValue.shortValue();
			currentField.set(object, shortValue);
		}
	}
	private void processFloatField(Node node, Object object, Field currentField, BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		Double doubleValue = this.babyCornXMLReader.evaluateXPathToNumber(node,babyCornXMLField.xPath());
		printTraces("Setting float on "+currentField.getName()+" Value: "+doubleValue);
		if(!doubleValue.isNaN())
		{
			float floatValue = doubleValue.floatValue();
			currentField.set(object, floatValue);
		}
	}
	private void processLongField(Node node, Object object, Field currentField, BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		Double doubleValue = this.babyCornXMLReader.evaluateXPathToNumber(node,babyCornXMLField.xPath());
		printTraces("Setting Long on "+currentField.getName()+" Value: "+doubleValue);
		if(!doubleValue.isNaN())
		{
			long longValue = doubleValue.longValue();
			currentField.set(object, longValue);
		}
	}
	private void processBooleanField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		String stringValue = this.babyCornXMLReader.evaluateXPathToString(node,babyCornXMLField.xPath());
		printTraces("Setting boolean on "+currentField.getName()+" Value: "+stringValue);
		boolean boolValue = false;
		if(stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("yes"))
		{
			boolValue = true;
		}
		currentField.set(object, boolValue);
	}
	private void processCharacterField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		String stringValue = this.babyCornXMLReader.evaluateXPathToString(node,babyCornXMLField.xPath());
		printTraces("Setting character on "+currentField.getName()+" Value: "+stringValue);
		char charValue = 0 ;
		if(stringValue.length()>=1)
		{
			charValue = stringValue.charAt(0);
		}
		currentField.set(object, charValue);
	}
	
	@SuppressWarnings("unchecked")
	private void processMapField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		NodeList nodeList = this.babyCornXMLReader.evaluateXPathToNodeList(node,babyCornXMLField.xPath());
		
		Map map = new HashMap();
		String key = babyCornXMLField.mapKey();
		String value = babyCornXMLField.mapValue();
		String keyValue ="";
		String valueValue="";
		for(int i = 0; i < nodeList.getLength(); i++) {                
                
			Node childNode = nodeList.item(i);
			String textContent =childNode.getTextContent();
			keyValue = key.trim().equalsIgnoreCase("text()")?textContent:"";
			valueValue = value.trim().equalsIgnoreCase("text()")?textContent:"";
			
            if (childNode.hasAttributes()) {
            	
            	if(key.trim().startsWith("@"))
            	{
            		Attr attr = (Attr) childNode.getAttributes().getNamedItem(key.replace("@", ""));
                    if (attr != null) {
                    	keyValue = attr.getValue();   
                    }
                }
            	if(value.trim().startsWith("@"))
            	{
            		Attr attr = (Attr) childNode.getAttributes().getNamedItem(value.replace("@", ""));
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
	private void processListField(Node node,Object object,Field currentField,BabyCornXMLField babyCornXMLField) throws XPathExpressionException, IllegalArgumentException, IllegalAccessException
	{
		NodeList nodeList = this.babyCornXMLReader.evaluateXPathToNodeList(node,babyCornXMLField.xPath());
		
		List list = new ArrayList();
		String value = babyCornXMLField.listValue();
		
		String valueValue="";
		for(int i = 0; i < nodeList.getLength(); i++) {                
                
			Node childNode = nodeList.item(i);
			String textContent =childNode.getTextContent();
			valueValue = value.trim().equalsIgnoreCase("text()")?textContent:"";
			
            if (childNode.hasAttributes()) {
            	
            	if(value.trim().startsWith("@"))
            	{
            		Attr attr = (Attr) childNode.getAttributes().getNamedItem(value.replace("@", ""));
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
	
	public Type[] getFieldParameterizedType(Field field)
	{
		Type type = field.getGenericType();
		
		if (type instanceof ParameterizedType) {
			
	        ParameterizedType parameterizedType = (ParameterizedType)type;
	        printTraces("Raw type is: " + parameterizedType.getRawType());
	        Type[] types = parameterizedType.getActualTypeArguments();
	        printTraces("parameterizedType: "+Arrays.toString(types));
	        return types;
		}
		return null;
	}
	
	public boolean isBasicType(Type type)
	{
		if(type.equals(String.class)
				||  type.equals(Integer.class) || type.equals(int.class)
        		|| 	type.equals(Boolean.class) || type.equals(boolean.class)
        		|| 	type.equals(Double.class) || type.equals(double.class)
        		|| 	type.equals(Character.class) || type.equals(char.class)
        		|| 	type.equals(Short.class) || type.equals(short.class)
        		|| 	type.equals(Long.class) || type.equals(long.class)
        		|| 	type.equals(Float.class) || type.equals(float.class)
        		|| 	type.equals(Byte.class) || type.equals(byte.class)
        )
        { return true;}
		return false;
	}
	// TODO: Improve this function
	public boolean isListOfObjects(Field field)
	{
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
	        ParameterizedType parameterizedType = (ParameterizedType)type;
	        printTraces("Raw type is: " + parameterizedType.getRawType());
	        
	        if(field.getType().equals(List.class) || field.getType().equals(ArrayList.class))
	        {
	        	Type argType = parameterizedType.getActualTypeArguments()[0]; // list will have only one argument
	        	if(isBasicType(argType))
	        	{
	        		printTraces("Argument Types: "+argType);
	        		return false;
	        	}
	        	
        		printTraces("Argument Types: "+argType);
        		return true;
	        }
	        return false;
	    } else {
	        System.out.println("Type: " + field.getType());
	        return false;
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
