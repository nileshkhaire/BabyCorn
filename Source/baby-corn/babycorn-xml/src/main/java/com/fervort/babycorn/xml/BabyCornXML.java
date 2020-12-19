package com.fervort.babycorn.xml;

import com.fervort.babycorn.xml.reader.BabyCornXMLReader;
import com.fervort.babycorn.xml.reader.BabyCornXMLReaderFactory;
import com.fervort.babycorn.xml.reader.BabyCornXMLReaderFactory.FactoryType;

public class BabyCornXML {
	
	private String xmlPath;
	private Object object;
	private BabyCornXMLReader babyCornXMLReader;
	
	public BabyCornXML(String xmlPath,Object object) {
		this.xmlPath= xmlPath;
		this.object= object;
		this.babyCornXMLReader = BabyCornXMLReaderFactory.getXMLReader(FactoryType.DEFAULT);
	}
	
		

}
