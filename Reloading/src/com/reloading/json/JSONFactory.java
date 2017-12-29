package com.reloading.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.Set;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.components.Case;
import com.reloading.components.CastBullet;
import com.reloading.components.Component;
import com.reloading.components.Firearm;
import com.reloading.components.Load;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.exceptions.ReloadingException;
import com.reloading.factory.Factory;
import com.reloading.sql.SqlFactory;
import com.reloading.target.TargetEvaluatorTest;
import com.reloading.testing.Test;

public class JSONFactory extends Factory {
	protected static final String BULLETFILE_KEY = "BULLETFILE";
	protected static final String CARTRIDGEFILE_KEY = "CARTRIDGEFILE";
	protected static final String CASEFILE_KEY = "CASEFILE";
	protected static final String FIREARMFILE_KEY = "FIREARMFILE";
	protected static final String POWDERFILE_KEY = "POWDERFILE";
	protected static final String PRIMERFILE_KEY = "PRIMERFILE";
	protected static final String LOADFILE_KEY = "LOADFILE";

	protected static final String BULLETID = "BulletId";
	protected static final String CARTRIDGEID = "CartridgeId";
	protected static final String CASEID = "CaseId";
	protected static final String FIREARMID = "FirearmId";
	protected static final String POWDERID = "PowderId";
	protected static final String PRIMERID = "PrimerId";

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

	public JSONFactory() {
		// TODO Auto-generated constructor stub
		bullets = new HashMap<Integer, Component>();
		cases = new HashMap<Integer, Component>();
		cartridges = new HashMap<Integer, Component>();
		firearms = new HashMap<Integer, Component>();
		powders = new HashMap<Integer, Component>();
		primers = new HashMap<Integer, Component>();
		loads = new HashMap<Integer, Component>();
	}

	public JSONFactory(String resourceFileName) {
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
		maxBulletID = setComponentMap(bullets, bulletsFileName, Constants.BULLETS);

		// Load Cartridges
		cartridgesFileName = propertyResourceBundle.getString(CARTRIDGEFILE_KEY);
		maxCartridgeID = setComponentMap(cartridges, cartridgesFileName, Constants.CARTRIDGES);

		// Load Cases
		casesFileName = propertyResourceBundle.getString(CASEFILE_KEY);
		maxCaseID = setCompositeComponentMap(cases, casesFileName, Constants.CASES);
		// Load Firearms
		firearmsFileName = propertyResourceBundle.getString(FIREARMFILE_KEY);
		maxFirearmID = setComponentMap(firearms, firearmsFileName, Constants.FIREARMS);

		// Load Powders
		powdersFileName = propertyResourceBundle.getString(POWDERFILE_KEY);
		maxPowderID = setComponentMap(powders, powdersFileName, Constants.POWDERS);

		// Load Primers
		primersFileName = propertyResourceBundle.getString(PRIMERFILE_KEY);
		maxPrimerID = setComponentMap(primers, primersFileName, Constants.PRIMERS);

		// Load loads
		loadsFileName = propertyResourceBundle.getString(LOADFILE_KEY);
		maxLoadID = setCompositeComponentMap(loads, loadsFileName, Constants.LOADS);

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
	private int setComponentMap(HashMap<Integer, Component> componentMap, String componentFileName, String arrayName) {
		int maxID = 0;
		System.out.println("ComponentFileName: " + componentFileName + "\tArrayName: " + arrayName);
		File componentFile = new File(componentFileName);
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(componentFileName));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray componentArray = (JSONArray) jsonObject.get(arrayName);
			for (int index = 0; index < componentArray.size(); index++) {
				JSONObject jSonComponent = (JSONObject) componentArray.get(index);
				String className = (String) jSonComponent.get(Constants.CLASSNAME);
				System.out.println("\n*******************************************");
				System.out.println("ClassName: " + className);
				Class componentClass = Class.forName(className);
				Component component = (Component) componentClass.newInstance();
				int id = Integer.parseInt((String) jSonComponent.get(Constants.ID));
				if (id > maxID) {
					maxID = id;
				}
				Set keySet = jSonComponent.keySet();
				Iterator iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String attributeName = (String) iterator.next();
					String value = (String) jSonComponent.get(attributeName);

					// System.out.println("\tattributeName: " + attributeName +
					// " Value: " + value);
					component.setAttribute(attributeName, value);
				}

				// System.out.println("*******************************************\n");
				component.setId(id);
				componentMap.put(new Integer(id), component);

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxID;
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
	private int setCompositeComponentMap(HashMap<Integer, Component> componentMap, String componentFileName,
			String arrayName) {
		int maxID = 0;
		System.out.println("ComponentFileName: " + componentFileName + "\tArrayName: " + arrayName);
		File componentFile = new File(componentFileName);
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(componentFileName));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray componentArray = (JSONArray) jsonObject.get(arrayName);
			for (int index = 0; index < componentArray.size(); index++) {
				JSONObject jSonComponent = (JSONObject) componentArray.get(index);
				String className = (String) jSonComponent.get(Constants.CLASSNAME);
				// System.out.println("\n*******************************************");
				// System.out.println("ClassName: " + className);
				Class componentClass = Class.forName(className);
				Component component = (Component) componentClass.newInstance();
				int id = Integer.parseInt((String) jSonComponent.get(Constants.ID));
				if (id > maxID) {
					maxID = id;
				}
				Set keySet = jSonComponent.keySet();
				Iterator iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String attrName = (String) iterator.next();
					String attrValue = (String) jSonComponent.get(attrName);
					Integer intVal;
					switch (attrName) {
					case BULLETID:
						intVal = new Integer(attrValue);
						// System.out.println("Bullet ID: " + attrValue);
						Bullet bullet = getBulletByID(intVal);
						className = bullet.getClass().getName();
						String bulletType = "Bullet";
						switch (className) {
						case Constants.CASTBULLET_CLASSNAME:
							bulletType = "CastBullet";
							break;

						}
						// System.out.println("\n***Adding Bullet: " + bullet +
						// " ****\n");
						component.setAttribute(bulletType, bullet);
						break;
					case CARTRIDGEID:
						intVal = new Integer(attrValue);
						Cartridge cartridge = getCartridgeByID(intVal);
						// System.out.println("CartridgeID: " + attrValue +
						// "\tAdding cartridge: " + cartridge);
						component.setAttribute(Constants.CARTRIDGE, cartridge);
						break;
					case CASEID:
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
						// System.out.println(attrName + " " + attrValue);
						component.setAttribute(attrName, attrValue);
						/*
						 * if (attrName.compareTo("Id") == 0){
						 * component.setId(attrValue); System.out.println("Id: "
						 * + component.getId());
						 * 
						 * }
						 */
						break;
					}
				}

				// System.out.println("*******************************************\n");
				component.setId(id);
				componentMap.put(new Integer(id), component);

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxID;
	}

	public int saveComponent(Component component, HashMap<Integer, Component> componentsMap, int maxID,
			String componentName, String componentFileName, String[] attributes) throws ReloadingException {
		int rtnMaxID = maxID;
		int id = component.getId();
		Integer iId = new Integer(id);
		if (component.getShortName().length() < 1) {
			ReloadingException exc = new ReloadingException("Name or Shortname is blank");
			throw exc;
		}
		if (id < 0) {
			rtnMaxID++;
			component.setId(rtnMaxID);
			iId = new Integer(rtnMaxID);
		}
		componentsMap.put(iId, component);
		JSONObject parentObject = new JSONObject();
		JSONArray componentsList = new JSONArray();
		parentObject.put(componentName, componentsList);

		Collection componentCollection = componentsMap.values();
		Iterator iterator = componentCollection.iterator();
		while (iterator.hasNext()) {
			Component mapComponent = (Component) iterator.next();
			JSONObject jSONComponent = new JSONObject();
			jSONComponent.put(Constants.CLASSNAME, mapComponent.getClass().getName());
			jSONComponent.put(Constants.ID, Integer.toString(mapComponent.getId()));
			jSONComponent.put(Constants.MANUFACTURER, mapComponent.getManufacturer());
			jSONComponent.put(Constants.NAME, mapComponent.getName());
			jSONComponent.put(Constants.SHORTNAME, mapComponent.getShortName());
			// TODO: Need to figure this one out
			for (int attIdx = 0; attIdx < attributes.length; attIdx++) {
				String attributeName = attributes[attIdx];
				System.out.println("attributeName: " + attributeName);
				String attributeValue = mapComponent.getAttribute(attributeName);
				jSONComponent.put(attributeName, attributeValue);
			}
			componentsList.add(jSONComponent);
		}

		try {
			FileWriter file = new FileWriter(componentFileName);

			file.write(parentObject.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return rtnMaxID;
	}

	/**
	 * Sets the resource for an XmlFactory
	 */
	public void setPropertyResourceBundle(PropertyResourceBundle propertyResourceBundle) {
		super.setPropertyResourceBundle(propertyResourceBundle);
		loadResources();
	}

	public Bullet getBulletByID(Integer id) {
		if (bullets.isEmpty()) {
			return null;
		} else if (!bullets.containsKey(id)) {
			return null;
		}
		return (Bullet) bullets.get(id);
	}

	@Override
	public Bullet getBulletByID(int id) {
		Integer iId = new Integer(id);
		return getBulletByID(iId);
	}

	@Override
	public ArrayList<Bullet> getBullets() {
		ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

		for (Integer key : bullets.keySet()) {
			Bullet bullet = (Bullet) bullets.get(key);
			bulletList.add(bullet);
		}
		Collections.sort(bulletList);
		return bulletList;
	}

	@Override
	public void saveBullet(Bullet bullet) throws ReloadingException {
		// TODO Auto-generated method stub

	}

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

	@Override
	public Cartridge getCartridgeByID(int id) {
		Integer iId = new Integer(id);
		return (Cartridge) cartridges.get(iId);
	};

	@Override
	public ArrayList<Cartridge> getCartridges() {
		ArrayList<Cartridge> cartridgeList = new ArrayList<Cartridge>();
		for (Integer key : cartridges.keySet()) {
			Cartridge cartridge = (Cartridge) cartridges.get(key);
			cartridgeList.add(cartridge);
		}
		Collections.sort(cartridgeList);
		return cartridgeList;
	}

	@Override
	public void saveCartridge(Cartridge cartridge) throws ReloadingException {
		maxCartridgeID = saveComponent(cartridge, cartridges, maxCartridgeID, Constants.CARTRIDGES, cartridgesFileName,
				Constants.CARTRIDGE_ATTRIBUTES);

	}

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

	@Override
	public ArrayList<Case> getCases() {
		ArrayList<Case> caseList = new ArrayList<Case>();
		for (Integer key : cases.keySet()) {
			Case nCase = (Case) cases.get(key);
			caseList.add(nCase);
		}

		Collections.sort(caseList);
		return caseList;
	}

	@Override
	public void saveCase(Case nCase) throws ReloadingException {
		maxCaseID = saveComponent(nCase, cases, maxCaseID, Constants.CASES, casesFileName, Constants.CASE_ATTRIBUTES);

	}

	/*****************************************************************/
	/* Firearm Handling */
	/*****************************************************************/

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

	
	@Override
	public Load getLoadByID(int id) {
		Integer iId = new Integer(id);
		return getLoadByID(iId);
	}

	public Load getLoadByID(Integer id) {
		if (loads.isEmpty()) {
			return null;
		} else if (!loads.containsKey(id)) {
			return null;
		}
		return (Load) loads.get(id);
	}

	@Override
	public ArrayList<Load> getLoads() {
		ArrayList<Load> loadList = new ArrayList<Load>();
		for (Integer key : loads.keySet()) {
			Load load = (Load) loads.get(key);
			loadList.add(load);
		}
		Collections.sort(loadList);
		return loadList;
	}

	@Override
	public ArrayList<Load> getLoadsByCartridgeId(int cartridgeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveLoad(Load load) throws ReloadingException {
		// TODO Auto-generated method stub

	}

	@Override
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

	@Override
	public ArrayList<Powder> getPowders() {
		ArrayList<Powder> powderList = new ArrayList<Powder>();
		for (Integer key : powders.keySet()) {
			Powder powder = (Powder) powders.get(key);
			powderList.add(powder);
		}
		Collections.sort(powderList);
		return powderList;
	}

	@Override
	public void savePowder(Powder powder) throws ReloadingException {
		maxPowderID = saveComponent(powder, powders, maxPowderID, Constants.POWDERS, powdersFileName,
				Constants.POWDER_ATTRIBUTES);

	}

	public Primer getPrimerByID(Integer id) {
		if (primers.isEmpty()) {
			return null;
		} else if (!primers.containsKey(id)) {
			return null;
		}
		return (Primer) primers.get(id);
	}

	@Override
	public Primer getPrimerByID(int id) {
		Integer iId = new Integer(id);
		return getPrimerByID(iId);
	}

	@Override
	public ArrayList<Primer> getPrimers() {
		ArrayList<Primer> primerList = new ArrayList<Primer>();
		for (Integer key : primers.keySet()) {
			Primer primer = (Primer) primers.get(key);
			primerList.add(primer);
		}
		return primerList;
	}

	@Override
	public void savePrimer(Primer primer) throws ReloadingException {
		maxPrimerID = saveComponent(primer, powders, maxPrimerID, Constants.PRIMERS, primersFileName,
				Constants.PRIMER_ATTRIBUTES);

	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public Test getTestByID(int id){
		Test test = new TargetEvaluatorTest();
		return test;
	}
	public ArrayList<Test> getTests(){
		ArrayList<Test> tests=  new ArrayList<Test>();
		return tests;
	}
	public void saveTest(Test test) throws ReloadingException{
		return;
	}
	
	public static void main(String args[]) {
		System.out.println("Configuration File: " + args[0]);
		JSONFactory factory = new JSONFactory(args[0]);

		System.out.println("\n*********************************************");
		System.out.println("            Bullets");
		System.out.println("*********************************************\n");

		ArrayList<Bullet> bullets = factory.getBullets();
		for (int bullIdx = 0; bullIdx < bullets.size(); bullIdx++) {
			Bullet bullet = bullets.get(bullIdx);
			System.out.println("Bullet: " + bullet.toString());
			if (bullet instanceof CastBullet) {
				CastBullet cb = (CastBullet) bullet;
				System.out.println("\t" + cb.getLube());
			}

		}
		System.out.println("\n*********************************************");
		System.out.println("            Cartridges");
		System.out.println("*********************************************\n");
		ArrayList<Cartridge> cartridges = factory.getCartridges();
		for (int idx = 0; idx < cartridges.size(); idx++) {
			Cartridge cartridge = cartridges.get(idx);
			System.out.println(cartridge.getShortName());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Cases");
		System.out.println("*********************************************\n");
		ArrayList<Case> cases = factory.getCases();
		for (int idx = 0; idx < cases.size(); idx++) {
			Case ncase = cases.get(idx);
			System.out.println(ncase.getShortName() + "\tcartridge: " + ncase.getCartridge().getShortName());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Powders");
		System.out.println("*********************************************\n");
		ArrayList<Powder> powders = factory.getPowders();
		for (int idx = 0; idx < powders.size(); idx++) {
			Powder powder = powders.get(idx);
			System.out.println(powder.getShortName());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Primers");
		System.out.println("*********************************************\n");
		ArrayList<Primer> primers = factory.getPrimers();
		for (int idx = 0; idx < primers.size(); idx++) {
			Primer primer = primers.get(idx);
			System.out.println(primer.getShortName());
		}
		ArrayList<Firearm> firearms = factory.getFirearms();
		System.out.println("maxFirearmID: ");
		for (int idx = 0; idx < firearms.size(); idx++) {
			Firearm fireArm =firearms.get(idx);
			System.out.println("Firearm: " + fireArm.getSerial());
		}
		
	}

}
