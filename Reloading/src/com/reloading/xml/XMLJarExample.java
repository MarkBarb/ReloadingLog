package com.reloading.xml;

import java.io.File;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLJarExample {

	public XMLJarExample() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for( int index=0;index<args.length;index++){
			System.out.println("Parsing: " + args[index]);
			parseXml(args[index]);
		}

	}
	public static void parseXml(String jarName){
		try {

			
			URL url = new URL("jar:file:" + jarName + "!/");
			System.out.println("url to index.jar is " + url.toString());
			JarURLConnection jarUrlConnection = (JarURLConnection) url.openConnection();
			JarFile jarFile = jarUrlConnection.getJarFile();
			Enumeration<JarEntry> iterator = jarFile.entries();

			//  try {
			while (iterator.hasMoreElements()) {
				JarEntry entry = iterator.nextElement();
				if (entry.getName().endsWith("word.xml")) {
					System.out.println("Found: " + entry.getName());
					InputStream inputStream = jarFile.getInputStream(entry);
					readWordXML(inputStream);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	private static void readWordXML(InputStream stream){
	
		try{
		//create the word list document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stream);
		doc.getDocumentElement().normalize();
		NodeList manualNodeList = doc.getElementsByTagName("manual");
		for (int nodeIdx=0;nodeIdx < manualNodeList.getLength();nodeIdx++){
			Node node = manualNodeList.item(nodeIdx);
			String manualName = "Boogers";
			if (node.hasAttributes()){
				//System.out.println(pad+ "\tAttributes:" );
				NamedNodeMap attributesMap = node.getAttributes();
				manualName = attributesMap.getNamedItem("name").getNodeValue();
				
			}
			System.out.println("Parsing manual: " + manualName);
			parseWords(node);
			System.out.println("Parsed manual: " + manualName);
		}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	public static void parseWords(Node manualNode){
		if (manualNode.hasChildNodes()){
			NodeList nList = manualNode.getChildNodes();
			for(int idx=0;idx<nList.getLength();idx++){
				Node wordNode = nList.item(idx);
				if (wordNode.getNodeName().compareToIgnoreCase("word") == 0){
					String term = "";
					if (wordNode.hasAttributes()){
						//System.out.println(pad+ "\tAttributes:" );
						NamedNodeMap attributesMap = wordNode.getAttributes();
						term = attributesMap.getNamedItem("term").getNodeValue();
					
					}
					System.out.println("\tProcessing Word: " + term);
					parseLocations(wordNode);
				}
			}
		}
		
	}
	public static void parseLocations(Node wordNode){
		if (wordNode.hasChildNodes()){
			NodeList nList = wordNode.getChildNodes();
			for(int idx=0;idx<nList.getLength();idx++){
				Node locationNode = nList.item(idx);
				if (locationNode.getNodeName().compareToIgnoreCase("used") == 0){
					if (locationNode.hasAttributes()){
						String location = "";
						NamedNodeMap attributesMap = locationNode.getAttributes();
						for (int attIdx = 0; attIdx<attributesMap.getLength();attIdx++){
							Node attr = attributesMap.item(attIdx);
							if (attr.getNodeName().compareToIgnoreCase("location") == 0){
								location = attr.getNodeValue();
							}
							System.out.println( "\t\tLocation: " + location);
						}
					}
					/*String location = "";
					if (locationNode.hasAttributes()){
						//System.out.println(pad+ "\tAttributes:" );
						NamedNodeMap attributesMap = wordNode.getAttributes();
						location = attributesMap.getNamedItem("location").getNodeValue();
					
					}
					System.out.println("\t\tProcessing Location: " + location);*/
					
					
				}
			}
		}
		
	}
	public static void parseNode(Node nNode,String pad){
		
		
			//Element element = (Element)nNode;
			//
			System.out.println(pad+ "Node:" + nNode.getNodeName() );
			String newPad = pad + "\t";
			if (nNode.hasAttributes()){
				//System.out.println(pad+ "\tAttributes:" );
				NamedNodeMap attributesMap = nNode.getAttributes();
				for (int attIdx = 0; attIdx<attributesMap.getLength();attIdx++){
					Node attr = attributesMap.item(attIdx);
					System.out.println(newPad + "\t" + attr.getNodeName() + "\t Value: " + attr.getNodeValue());
				}
			}
			NodeList nList = nNode.getChildNodes();
			for(int idx=0;idx<nList.getLength();idx++){
				Node newNode = nList.item(idx);
				parseNode(newNode,newPad);
				
			}
		}
}
