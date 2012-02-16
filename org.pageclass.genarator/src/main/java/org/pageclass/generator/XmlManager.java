package org.pageclass.generator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlManager {
	private String PATH="config.xml";
	
	public List<String> getSettings(String xpath) {
		List <String> res = new ArrayList<String>();
		try{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(PATH);
		XPathFactory factory = XPathFactory.newInstance();
		XPath xp = factory.newXPath();
		XPathExpression expr = xp.compile(xpath);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			res.add(nodes.item(i).getNodeValue());
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
