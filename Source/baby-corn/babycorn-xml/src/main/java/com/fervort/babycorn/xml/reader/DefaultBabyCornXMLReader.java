package com.fervort.babycorn.xml.reader;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class DefaultBabyCornXMLReader implements BabyCornXMLReader {

	Document doc = null;
	XPath xPath;
	
	@Override
	public void initParser(String xmlPath) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.doc = builder.parse(xmlPath);
	}

	@Override
	public void initXPath() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        xPath = xpathFactory.newXPath();		
	}
	
	public Document getDocumentRoot() {
		return this.doc;
	}
	
	@Override
	public String evaluateXPathToString(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr = this.xPath.compile(inputPath);
        return (String) expr.evaluate(doc, XPathConstants.STRING);
	}
	
	@Override
	public String evaluateXPathToString(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr = this.xPath.compile(inputPath);
        return (String) expr.evaluate(object, XPathConstants.STRING);
	}
	
	@Override
	public NodeList evaluateXPathToNodeList(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		return nodes;
	}
	
	@Override
	public NodeList evaluateXPathToNodeList(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        NodeList nodes = (NodeList) expr.evaluate(object, XPathConstants.NODESET);
		return nodes;
	}
	
	@Override
	public Node evaluateXPathToNode(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}
	
	@Override
	public Node evaluateXPathToNode(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Node node = (Node) expr.evaluate(object, XPathConstants.NODE);
		return node;
	}
	
	@Override
	public Boolean evaluateXPathToBoolean(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Boolean boool =  (Boolean) expr.evaluate(doc, XPathConstants.BOOLEAN);
		return boool;
	}
	
	@Override
	public Boolean evaluateXPathToBoolean(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Boolean boool =  (Boolean) expr.evaluate(object, XPathConstants.BOOLEAN);
		return boool;
	}
	
	@Override
	public Double evaluateXPathToDouble(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Double doubleValue =  (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		return doubleValue;
	}
	
	@Override
	public Double evaluateXPathToDouble(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Double doubleValue =  (Double) expr.evaluate(object, XPathConstants.NUMBER);
		return doubleValue;
	}
	
	@Override
	public int evaluateXPathToInteger(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
		double doubleValue =  (double) expr.evaluate(doc, XPathConstants.NUMBER);
		return (int)doubleValue;
	}
	
	@Override
	public int evaluateXPathToInteger(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        double doubleValue =  (double) expr.evaluate(object, XPathConstants.NUMBER);
		return (int)doubleValue;
	}
	

}