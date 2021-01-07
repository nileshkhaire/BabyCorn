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

package com.fervort.babycorn.xml.reader;

import java.io.IOException;

import javax.xml.namespace.QName;
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
	
	@Override
	public Document getDocumentRoot() {
		return this.doc;
	}
	
	@Override
	public Object evaluateXPath(String inputPath,QName qName) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        return expr.evaluate(doc, qName);
	}
	
	@Override
	public Object evaluateXPath(Object object,String inputPath,QName qName) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        return expr.evaluate(object, qName);
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
	public Double evaluateXPathToNumber(String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
		Double doubleValue =  (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		return doubleValue;
	}
	
	@Override
	public Double evaluateXPathToNumber(Object object,String inputPath) throws XPathExpressionException
	{
		XPathExpression expr =  xPath.compile(inputPath);
        Double doubleValue =  (Double) expr.evaluate(object, XPathConstants.NUMBER);
		return doubleValue;
	}
	

}