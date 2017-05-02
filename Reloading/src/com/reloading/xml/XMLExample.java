package com.reloading.xml;
import java.io.File;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

//import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class XMLExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for( int index=0;index<args.length;index++){
			System.out.println("Parsing: " + args[index]);
			parseXml(args[index]);
		}

	}

	private static void parseXml(String xmlFileName){
		try {

			File fXmlFile = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList preferenceList = doc.getElementsByTagName("manual");
			for (int nodeIdx=0;nodeIdx < preferenceList.getLength();nodeIdx++){
				Node node = preferenceList.item(nodeIdx);
				
				parseNode(node,"");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void parseNode(Node nNode,String pad){
		
		//Element element = (Element)nNode;
		System.out.println(pad+ "Node:" + nNode.getNodeName() );
		if (nNode.hasAttributes()){
			System.out.println(pad+ "\tAttributes:" );
			NamedNodeMap attributesMap = nNode.getAttributes();
			for (int attIdx = 0; attIdx<attributesMap.getLength();attIdx++){
				Node attr = attributesMap.item(attIdx);
				System.out.println(pad+ "\t\t" + attr.getNodeName() + "\t" + attr.getNodeValue());
			}
		}
		NodeList nList = nNode.getChildNodes();
		String newPad = pad + "\t";
		for(int idx=0;idx<nList.getLength();idx++){
			Node newNode = nList.item(idx);
			parseNode(newNode,newPad);
			
		}
	}

}
