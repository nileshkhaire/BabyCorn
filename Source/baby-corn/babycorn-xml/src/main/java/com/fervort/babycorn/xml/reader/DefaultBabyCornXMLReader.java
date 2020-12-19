package com.fervort.babycorn.xml.reader;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
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

	@Override
	public String evaluateXPathToString(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr = this.xPath.compile(inputPath);
        return (String) expr.evaluate(doc, XPathConstants.STRING);
	}
	
	@Override
	public NodeList evaluateXPathToNodeList(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
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
	public Boolean evaluateXPathToBoolean(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Boolean boool =  (Boolean) expr.evaluate(doc, XPathConstants.BOOLEAN);
		return boool;
	}
	
	@Override
	public Double evaluateXPathToDouble(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Double doubleValue =  (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		return doubleValue;
	}
	

}