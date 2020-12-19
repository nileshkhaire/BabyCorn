package com.fervort.babycorn.xml.reader;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public interface BabyCornXMLReader {

	void initParser(String xmlPath) throws ParserConfigurationException, SAXException, IOException;
	void initXPath();
	public String evaluateXPathToString(String inputPath) throws XPathExpressionException;
	public NodeList evaluateXPathToNodeList(String inputPath) throws XPathExpressionException;
	public Node evaluateXPathToNode(String inputPath) throws XPathExpressionException;
	public Boolean evaluateXPathToBoolean(String inputPath) throws XPathExpressionException;
	public Double evaluateXPathToDouble(String inputPath) throws XPathExpressionException;
}
