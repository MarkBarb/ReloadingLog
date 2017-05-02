package com.reloading.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PropertyResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.components.Case;
import com.reloading.components.CastBullet;
import com.reloading.components.Component;
import com.reloading.components.FactoryLoad;
import com.reloading.components.Firearm;
import com.reloading.components.Load;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.components.Reload;
import com.reloading.exceptions.ReloadingException;
import com.reloading.factory.Factory;

public class XmlFactory extends Factory {

	protected static final String BULLETFILE_KEY = "BULLETFILE";
	protected static final String CARTRIDGEFILE_KEY = "CARTRIDGEFILE";
	protected static final String CASEFILE_KEY = "CASEFILE";
	protected static final String FIREARMFILE_KEY = "FIREARMFILE";
	protected static final String POWDERFILE_KEY = "POWDERFILE";
	protected static final String PRIMERFILE_KEY = "PRIMERFILE";
	protected static final String LOADFILE_KEY = "LOADFILE";
	

	protected static final String BULLETID = "BulletId";
	protected static final String CARTRIDGEID = "CartridgeId";
	protected static final String CASEID =  "CaseId";
	protected static final String FIREARMID =  "FirearmId";
	protected static final String POWDERID =  "PowderId";
	protected static final String PRIMERID =  "PrimerId";
	
	

	private NodeList bulletsNodeList;
	// private NodeList cartidgesNodeList;
	private int maxBulletID = 0;
	private int maxCartridgeID = 0;
	private int maxCaseID = 0;
	private int maxFirearmID = 0;
	private int maxPowderID = 0;
	private int maxPrimerID = 0;
	private int maxLoadID = 0;

	private String bulletsFileName = "";
	private String cartridgesFileName = "";
	private String casesFileName = "";
	private String firearmsFileName = "";
	private String powdersFileName = "";
	private String primersFileName = "";
	private String loadsFileName = "";

	protected HashMap<Integer, Component> bullets;
	protected HashMap<Integer, Component> cartridges;
	protected HashMap<Integer, Component> cases;
	protected HashMap<Integer, Component> firearms;
	protected HashMap<Integer, Component> powders;
	protected HashMap<Integer, Component> primers;
	protected HashMap<Integer, Component> loads;

	// static Logger log4j = Logger.getLogger(XmlFactory.class.getName());
	public XmlFactory() {
		super();
		bullets = new HashMap<Integer, Component>();
		cases = new HashMap<Integer, Component>();
		cartridges = new HashMap<Integer, Component>();
		firearms = new HashMap<Integer, Component>();
		powders = new HashMap<Integer, Component>();
		primers = new HashMap<Integer, Component>();
		loads = new HashMap<Integer, Component>();
	}

	public XmlFactory(String resourceFileName) {
		super(resourceFileName);
		bullets = new HashMap<Integer, Component>();
		cases = new HashMap<Integer, Component>();
		cartridges = new HashMap<Integer, Component>();
		firearms = new HashMap<Integer, Component>();
		powders = new HashMap<Integer, Component>();
		primers = new HashMap<Integer, Component>();
		loads = new HashMap<Integer, Component>();
		loadResources();
	}

	/**
	 * Loads the data from each individual xml file specified in the resource
	 * bundle.
	 */
	private void loadResources() {
		// Load Bullets
		bulletsFileName = propertyResourceBundle.getString(BULLETFILE_KEY);
		maxBulletID = setComponentMap(bullets, bulletsFileName, Constants.BULLET);

		// Load Cartridges
		cartridgesFileName = propertyResourceBundle.getString(CARTRIDGEFILE_KEY);
		maxCartridgeID = setComponentMap(cartridges, cartridgesFileName, Constants.CARTRIDGE);

		// Load Cases
		casesFileName = propertyResourceBundle.getString(CASEFILE_KEY);
		maxCaseID = setComponentMap(cases, casesFileName, Constants.CASE);

		// Load Firearms
		firearmsFileName = propertyResourceBundle.getString(FIREARMFILE_KEY);
		
		maxFirearmID = setComponentMap(firearms, firearmsFileName, Constants.FIREARM);
		
		// Load Powders
		powdersFileName = propertyResourceBundle.getString(POWDERFILE_KEY);
		maxPowderID = setComponentMap(powders, powdersFileName, Constants.POWDER);

		// Load Primers
		primersFileName = propertyResourceBundle.getString(PRIMERFILE_KEY);
		maxPrimerID = setComponentMap(primers, primersFileName, Constants.PRIMER);
		
		// Load loads
		loadsFileName = propertyResourceBundle.getString(LOADFILE_KEY);
		maxLoadID = setCompositeComponentMap(loads,loadsFileName,Constants.LOAD);
		
	}

	/**
	 * Sets the resource for an XmlFactory
	 */
	public void setPropertyResourceBundle(PropertyResourceBundle propertyResourceBundle) {
		super.setPropertyResourceBundle(propertyResourceBundle);
		loadResources();
	}

	/**
	 * 
	 * @param componentMap
	 *            The map that will hold the components
	 * @param componentFileName
	 *            The filename of the xml data for this component
	 * @param nodeName
	 *            The name of the XMLNode that the data will be held in.
	 * @return Returns the maximum id of the particular type of component that
	 *         will be loaded
	 */
	private int setComponentMap(HashMap<Integer, Component> componentMap, String componentFileName, String nodeName) {
		int maxID = 0;
		System.out.println("setComponentMap(" + componentMap.size() + " " + componentFileName + " " + nodeName);
		File componentFile = new File(componentFileName);
		try {
			InputStream input = new FileInputStream(componentFile);
			Document orderDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			NodeList nodeList = orderDocument.getElementsByTagName(nodeName);
			for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {
				Node node = nodeList.item(nodeIdx);
				if (node.hasAttributes()) {
					NamedNodeMap attributes = node.getAttributes();
					String className = attributes.getNamedItem(Constants.CLASSNAME).getTextContent();

					// Instantiate the class

					Class componentClass = Class.forName(className);
					Component component = (Component) componentClass.newInstance();

					for (int attrIdx = 0; attrIdx < attributes.getLength(); attrIdx++) {
						Node attr = attributes.item(attrIdx);
						String attrName = attr.getNodeName();
						String attrValue = attr.getNodeValue().toString();

						component.setAttribute(attrName, attrValue);
					}
					componentMap.put(new Integer(component.getId()), component);
					// Needed to make sure we have unique ids
					if (maxID < component.getId()) {
						maxID = component.getId();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return maxID;
	}
	
	protected int setCompositeComponentMap(HashMap<Integer, Component> componentMap, String componentFileName, String nodeName) {
		int maxID = 0;
		File componentFile = new File(componentFileName);
		
		try {
			InputStream input = new FileInputStream(componentFile);
			Document orderDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			NodeList nodeList = orderDocument.getElementsByTagName(nodeName);
			for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {
				Node node = nodeList.item(nodeIdx);
				if (node.hasAttributes()) {
					NamedNodeMap attributes = node.getAttributes();
					String className = attributes.getNamedItem(Constants.CLASSNAME).getTextContent();
					//System.out.println("\n**************************************************");
					//System.out.println("instantiating Classname: " + className);
					//System.out.println("**************************************************\n");
					// Instantiate the class

					Class componentClass = Class.forName(className);
					Component component = (Component) componentClass.newInstance();
					//Load component = (Load) componentClass.newInstance();

					for (int attrIdx = 0; attrIdx < attributes.getLength(); attrIdx++) {
						Node attr = attributes.item(attrIdx);
						String attrName = attr.getNodeName();
						String attrValue = attr.getNodeValue().toString();
						Integer intVal;
						switch(attrName){
						case BULLETID:
							intVal = new Integer(attrValue);
							//System.out.println("Bullet ID: " + attrValue);
							Bullet bullet = getBulletByID(intVal);
							className = bullet.getClass().getName();
							String bulletType = "Bullet";
							switch(className){
							case Constants.CASTBULLET_CLASSNAME:
								bulletType = "CastBullet";
								break;
								
							}					
							//System.out.println("\n***Adding Bullet: " + bullet + " ****\n");
							component.setAttribute(bulletType, bullet);
							break;
						case CARTRIDGEID :
							intVal = new Integer(attrValue);
							Cartridge cartridge = getCartridgeByID(intVal);
							//System.out.println("Adding cartridge: " + cartridge);
							component.setAttribute(Constants.CARTRIDGE, cartridge);
							break;
						case CASEID :
							intVal = new Integer(attrValue);
							Case nCase = getCaseByID(intVal);
							component.setAttribute(Constants.CASE, nCase);
							break;
						case POWDERID:
							intVal = new Integer(attrValue);
							Powder powder = getPowderByID(intVal);
							component.setAttribute(Constants.POWDER, powder);
							break;
						case PRIMERID:
							intVal = new Integer(attrValue);
							Primer primer = getPrimerByID(intVal);
							component.setAttribute(Constants.PRIMER, primer);
							break;
						case Constants.CLASSNAME:
							break;
						default:
							//System.out.println(attrName + " " + attrValue);
							component.setAttribute(attrName, attrValue);
							/*if (attrName.compareTo("Id") == 0){
								component.setId(attrValue);
								System.out.println("Id: " + component.getId());
								
							}*/
							break;
						}

					}
					//System.out.println("component.getId()" + component.getId());
					componentMap.put(new Integer(component.getId()), component);
					// Needed to make sure we have unique ids
					if (maxID < component.getId()) {
						maxID = component.getId();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return maxID;
		
	}
	/**
	 * 
	 * @param component
	 * @param componentsMap
	 * @param maxID
	 * @param componentName
	 * @param componentFileName
	 * @param attributes
	 * @return
	 * @throws ReloadingException
	 */
	
	public int saveComponent(Component component, HashMap<Integer, Component> componentsMap, int maxID,
			String componentName, String componentFileName, String[] attributes) throws ReloadingException {
		int rtnMaxID = maxID;
		int id = component.getId();
		Element xmlComponent = null;

		/*System.out.println("Adding component: \n" 
				+ component + "\n\t"
				+"HashMap<Integer, Component> componentsMap \n\t"
				+ maxID + "\n\t"
				+ componentName + "\n\t"
				+ componentFileName+ "\n\t"
				+ "attributes:");
				
		for (int idx=0;idx< attributes.length; idx++){
			System.out.println("\t\t" + attributes[idx]);
		}
		System.out.println("maxID: " + maxID);
		 * 
		 */
		if (component.getShortName().length() < 1) {
			ReloadingException exc = new ReloadingException("Name or Shortname is blank");
			throw exc;
		}
		//
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(componentFileName);
			
			// Add if id < 0,
			if (id < 0) {
				rtnMaxID++;
				component.setId(rtnMaxID);
				Element root = document.getDocumentElement();
				xmlComponent = document.createElement(componentName);
				//System.out.println("ClassName: " + component.getClass().getName());
				xmlComponent.setAttribute(Constants.CLASSNAME, component.getClass().getName());
				xmlComponent.setAttribute(Constants.ID, Integer.toString(component.getId()));
				root.appendChild(xmlComponent);

			} // Otherwise update
			else {

				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				String search = "//" + componentName + "[@Id=\"" + Integer.toString(component.getId()) + "\"]";
				XPathExpression expr = xpath.compile(search);
				NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

				if (nl != null && nl.getLength() > 0) {
					xmlComponent = (Element) nl.item(0);
				}
			}
			if (xmlComponent != null) {
				//System.out.println(component.getManufacturer() +"\t" + component.getName() + "\t" + component.getShortName());
				xmlComponent.setAttribute(Constants.MANUFACTURER , component.getManufacturer());
				xmlComponent.setAttribute(Constants.NAME, component.getName());
				xmlComponent.setAttribute(Constants.SHORTNAME, component.getShortName());
				for (int attIdx=0;attIdx<attributes.length;attIdx++){
					String attributeName = attributes[attIdx];
					String attributeValue = component.getAttribute(attributeName);
					xmlComponent.setAttribute(attributeName, attributeValue);
				}
				
				DOMSource source = new DOMSource(document);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				StreamResult result = new StreamResult(componentFileName);
				transformer.transform(source, result);

				// update or replace bullet in hashmap
				Integer iId = new Integer(component.getId());
				if (componentsMap.containsKey(iId)) {
					// bullets.get(iId).setName(bullet.getName());
					componentsMap.put(iId,component);
				} else {
					componentsMap.put(iId, component);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return rtnMaxID;
	};

	/*****************************************************************/
	/* Bullet Handling */
	/*****************************************************************/

	/**
	 * 
	 */
	public Bullet getBulletByID(int id) {
		Integer iId = new Integer(id);
		return getBulletByID(iId);
	}

	public Bullet getBulletByID(Integer id) {
		if (bullets.isEmpty()) {
			return null;
		} else if (!bullets.containsKey(id)) {
			return null;
		}
		return (Bullet) bullets.get(id);
	}

	public ArrayList<Bullet> getBullets() {
		ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

		for (Integer key : bullets.keySet()) {
			Bullet bullet = (Bullet) bullets.get(key);
			bulletList.add(bullet);
		}
		Collections.sort(bulletList);
		return bulletList;
	}

	public void saveBullet(Bullet bullet) throws ReloadingException {
		
		String [] attributes = {};
		String bulletType = "";
		String className = bullet.getClass().getName();
		System.out.println(className);
		switch (className){
		case Constants.BULLET_CLASSNAME:
		
			attributes = Constants.BULLET_ATTRIBUTES;
			break;
			
		case Constants.CASTBULLET_CLASSNAME:
			attributes = Constants.CASTBULLET_ATTRIBUTES;
			break;
		}
		System.out.println("saveBullet: bulletType: " + bulletType + "\nAttributes:");
		for (int idx=0;idx< attributes.length; idx++){
			System.out.println("\t\t" + attributes[idx]);
		}
		maxBulletID=  saveComponent(bullet
				,bullets
				,maxBulletID
				,Constants.BULLET
				,bulletsFileName 
				,attributes );
		
	};

	/*****************************************************************/
	/* Cartridge Handling */
	/*****************************************************************/

	/**
	 * 
	 */
	public Cartridge getCartridgeByID(Integer id) {
		if (cartridges.isEmpty()) {
			return null;
		} else if (!cartridges.containsKey(id)) {
			return null;
		}
		return (Cartridge) cartridges.get(id);
	};

	public Cartridge getCartridgeByID(int id) {
		Integer iId = new Integer(id);
		return (Cartridge) cartridges.get(iId);
	};

	public ArrayList<Cartridge> getCartridges() {
		ArrayList<Cartridge> cartridgeList = new ArrayList<Cartridge>();
		for (Integer key : cartridges.keySet()) {
			Cartridge cartridge = (Cartridge) cartridges.get(key);
			cartridgeList.add(cartridge);
		}
		Collections.sort(cartridgeList);
		return cartridgeList;
	}


	public void saveCartridge(Cartridge cartridge) throws ReloadingException {
		
		maxCartridgeID=  saveComponent(cartridge
				,cartridges
				,maxCartridgeID
				,Constants.CARTRIDGE
				,cartridgesFileName 
				,Constants.CARTRIDGE_ATTRIBUTES );
	};/*****************************************************************/
	/* Case Handling */
	/*****************************************************************/

	/**
	 * 
	 */
	public Case getCaseByID(Integer id) {
		if (cases.isEmpty()) {
			return null;
		} else if (!cases.containsKey(id)) {
			return null;
		}
		return (Case) cases.get(id);
	};

	public Case getCaseByID(int id) {
		Integer iId = new Integer(id);
		return (Case) cases.get(iId);
	};

	public ArrayList<Case> getCases() {
		ArrayList<Case> caseList = new ArrayList<Case>();
		for (Integer key : cases.keySet()) {
			Case nCase = (Case) cases.get(key);
			caseList.add(nCase);
		}

		Collections.sort(caseList);
		return caseList;
	}

	public void saveCase(Case nCase) throws ReloadingException {
		System.out.println("Saving Case to file: " + casesFileName);
		maxCaseID=  saveComponent(nCase
				,cases
				,maxCaseID
				,Constants.CASE
				,casesFileName 
				,Constants.CASE_ATTRIBUTES );
	};


	/****************************************************************/
	/* Load Handling                                                */
	/****************************************************************/
	
	public  Load getLoadByID(int id){
		Integer iId = new Integer(id);
		return getLoadByID(iId);
	}
	
	public  Load getLoadByID(Integer id){
		if (loads.isEmpty()) {
			return null;
		} else if (!loads.containsKey(id)) {
			return null;
		}
		return (Load) loads.get(id);
	}
	public ArrayList<Load> getLoads(){
		ArrayList<Load> loadList = new ArrayList<Load>();
		for (Integer key : loads.keySet()) {
			Load load = (Load) loads.get(key);
			loadList.add(load);
		}
		Collections.sort(loadList);
		return loadList;
	}
	
	public  ArrayList<Load> getLoadsByCartridgeId(int cartridgeId){
		ArrayList<Load> loadList = new ArrayList<Load>();
		for (Integer key : loads.keySet()) {
			Load load = (Load) loads.get(key);
			int currCartId = load.getCartridgeId();
			if (cartridgeId == currCartId){
				loadList.add(load);
			}
		}
		Collections.sort(loadList);
		return loadList;
	}
	
	public  void saveLoad(Load load)throws ReloadingException{ 
		String [] attributes = Constants.FACTORYLOAD_ATTRIBUTES;
		String loadType = Constants.LOAD;
		String className = load.getClass().getName();
		
		switch (className){
		case Constants.FACTORYLOAD_CLASSNAME:
			saveFactoryLoad((FactoryLoad)load);
			break;
		case Constants.RELOAD_CLASSNAME:
			saveReload((Reload)load);
			break;
			default:
				System.out.println("Unknown Load Type");
		}
		
	}

	private  void saveFactoryLoad(FactoryLoad load)throws ReloadingException{ 
		String [] attributes = Constants.FACTORYLOAD_ATTRIBUTES;
		String loadType = Constants.LOAD;
		String className = load.getClass().getSimpleName();
		
		maxLoadID=  saveComponent(load
				,loads
				,maxLoadID
				,loadType
				,loadsFileName 
				,attributes );
		
	}
	
	private  void saveReload(Reload load)throws ReloadingException{ 
		String [] attributes = Constants.RELOAD_ATTRIBUTES;
		String loadType = Constants.LOAD;
		String className = load.getClass().getSimpleName();
		
		maxLoadID=  saveComponent(load
				,loads
				,maxLoadID
				,loadType
				,loadsFileName 
				,attributes );
		
	}
	

	/*****************************************************************/
	/* Powder Handling */
	/*****************************************************************/

	/**
	 * 
	 */
	public Powder getPowderByID(int id) {
		Integer iId = new Integer(id);
		return getPowderByID(iId);
	}

	public Powder getPowderByID(Integer id) {
		if (powders.isEmpty()) {
			return null;
		} else if (!powders.containsKey(id)) {
			return null;
		}
		return (Powder) powders.get(id);
	}

	public ArrayList<Powder> getPowders() {
		ArrayList<Powder> powderList = new ArrayList<Powder>();
		for (Integer key : powders.keySet()) {
			Powder powder = (Powder) powders.get(key);
			powderList.add(powder);
		}
		Collections.sort(powderList);
		return powderList;
	}
	public void savePowder(Powder powder) throws ReloadingException {
		maxPowderID=  saveComponent(powder
				,powders
				,maxPowderID
				,Constants.POWDER
				,powdersFileName 
				,Constants.POWDER_ATTRIBUTES );
	}


	/*****************************************************************/
	/* Primer Handling */
	/*****************************************************************/

	/**
	 * 
	 */
	public Primer getPrimerByID(int id) {
		Integer iId = new Integer(id);
		return getPrimerByID(iId);
	}

	public Primer getPrimerByID(Integer id) {
		if (primers.isEmpty()) {
			return null;
		} else if (!primers.containsKey(id)) {
			return null;
		}
		return (Primer) primers.get(id);
	}

	public ArrayList<Primer> getPrimers() {
		ArrayList<Primer> primerList = new ArrayList<Primer>();
		for (Integer key : primers.keySet()) {
			Primer primer = (Primer) primers.get(key);
			primerList.add(primer);
		}
		return primerList;
	}
	
	public void savePrimer(Primer primer) throws ReloadingException {
		maxPrimerID=  saveComponent(primer
				,primers
				,maxPrimerID
				,Constants.PRIMER
				,primersFileName 
				,Constants.PRIMER_ATTRIBUTES );
	}
	@Override
	public Firearm getFirearmByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Firearm> getFirearms() {

		ArrayList<Firearm> firearmList = new ArrayList<Firearm>();
		for (Integer key : firearms.keySet()) {
			Firearm firearm = (Firearm) firearms.get(key);
			firearmList.add(firearm);
		}
		return firearmList;
	}

	@Override
	public void saveFirearm(Firearm firearm) throws ReloadingException {
		maxFirearmID=  saveComponent(firearm
				,firearms
				,maxFirearmID
				,Constants.FIREARM
				,firearmsFileName 
				,Constants.FIREARM_ATTRIBUTES );
		
	}

	/*****************************************************************/
	/* main */
	/*****************************************************************/


	public static void main(String args[]) {
		XmlFactory xmlFactory = new XmlFactory(args[0]);
		// xmlFactory.setBaseDirectory(args[0]);
		System.out.println(args[0]);

		ArrayList<Bullet> bullets = xmlFactory.getBullets();
		for (int bullIdx = 0; bullIdx < bullets.size(); bullIdx++) {
			Bullet bullet = bullets.get(bullIdx);
			System.out.println("Bullet: " + bullet.toString());
			if (bullet instanceof CastBullet) {
				CastBullet cb = (CastBullet) bullet;
				System.out.println("\t" + cb.getLube());
			}

		}

		ArrayList<Cartridge> cartridges = xmlFactory.getCartridges();

		for (int idx = 0; idx < cartridges.size(); idx++) {
			Cartridge cartridge = cartridges.get(idx);
			System.out.println("Cartridge: " + cartridge);
		}
		
		ArrayList<Firearm> firearms = xmlFactory.getFirearms();
		System.out.println("maxFirearmID: ");
		for (int idx = 0; idx < firearms.size(); idx++) {
			Firearm fireArm =firearms.get(idx);
			System.out.println("Firearm: " + fireArm.getSerial());
		}
		
	}

}
