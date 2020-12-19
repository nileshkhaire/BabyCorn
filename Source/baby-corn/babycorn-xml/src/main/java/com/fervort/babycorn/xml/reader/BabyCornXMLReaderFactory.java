package com.fervort.babycorn.xml.reader;

public class BabyCornXMLReaderFactory {

	public enum FactoryType
	{
		DEFAULT
	}
	public static BabyCornXMLReader getXMLReader(FactoryType factoryType){
		
		BabyCornXMLReader xmlReader = null;
		
		switch (factoryType) {
			case DEFAULT:
				xmlReader = new DefaultBabyCornXMLReader();
				break;
			default:
				break;
		}
		
		return xmlReader;
		
	}
}
