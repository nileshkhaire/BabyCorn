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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public interface BabyCornXMLReader {

	void initParser(String xmlPath) throws ParserConfigurationException, SAXException, IOException;
	void initXPath();
	Document getDocumentRoot();
	public String evaluateXPathToString(String inputPath) throws XPathExpressionException;
	public String evaluateXPathToString(Object object,String inputPath) throws XPathExpressionException;
	public NodeList evaluateXPathToNodeList(String inputPath) throws XPathExpressionException;
	public NodeList evaluateXPathToNodeList(Object object,String inputPath) throws XPathExpressionException;
	public Node evaluateXPathToNode(String inputPath) throws XPathExpressionException;
	public Node evaluateXPathToNode(Object object,String inputPath) throws XPathExpressionException;
	public Double evaluateXPathToNumber(String inputPath) throws XPathExpressionException;
	public Double evaluateXPathToNumber(Object object, String inputPath) throws XPathExpressionException;
}
