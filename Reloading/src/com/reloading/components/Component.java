package com.reloading.components;

import java.lang.reflect.Method;

import com.reloading.Constants;
/**
 * A component is an object that is used in the reloading log.
 * The getAttribute and setAttribute method uses introspection 
 * to get and set attributes. 
 * @author Mark Barb
 *
 */
public abstract class Component {
	int id = -1;
	String manufacturer = "";
	String shortName = "";
	String name = "";

	/**
	 * 
	 */
	public Component() {
		super();
	}
	public Component(int id) {
		super();
		this.id = id;
	}

	/**
	 * Returns a String representation of the attribute via introspection.  
	 * A method named getAttributeName.  For example, if you wanted to retrieve
	 * the id of a component, a getId() method must exist in the component. 
	 * @param attributeName
	 * @return string representation of the attribute.
	 */
	public String getAttribute(String attributeName){
		String methodName = "get" + attributeName;
		String rtnVal = "";
		try {
			Class<? extends Object> objectClass = this.getClass();
			Method method;
			method = objectClass.getMethod(methodName);
			rtnVal = String.valueOf(method.invoke(this));
		}
		catch (Exception e) {
			return rtnVal;
		}
		return rtnVal;
	}
	
	/**
	 * Returns id of component
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns Manufacturer of component
	 * @return
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * Returns a long, discriptive name of the component
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a shorter version of the name.  This is usually used for
	 * displaying in dropdowns, tables, etc
	 * @return
	 */
	public String getShortName() {
		return shortName;
	}
	
	
	/**
	 * 
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public boolean setAttribute(String attributeName, Object value){
		String methodName = "set" + attributeName;
		//System.out.println(methodName);
		if (attributeName.equals(Constants.CLASSNAME)){
			return true;
		}
		try {
			Class<? extends Object> objectClass = this.getClass();
			
			Method method;
			method = objectClass.getMethod(methodName, value.getClass());
			method.invoke(this, value);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Sets the id of the component using a string
	 * @param id
	 */
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}
	
	/**
	 * Sets the id of the component using an int
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Sets Manufacturer
	 * @param manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	/**
	 * Sets the descriptive Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the name for displaying
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Override
	/**
	 * Defaults the toString method to display the short name.
	 */
	public String toString(){
		return shortName;
	}
}
