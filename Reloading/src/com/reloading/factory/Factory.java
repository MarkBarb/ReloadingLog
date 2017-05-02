package com.reloading.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PropertyResourceBundle;

import com.reloading.exceptions.*;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;



import com.reloading.components.*;


public abstract class Factory {
	protected PropertyResourceBundle propertyResourceBundle;
	//static Logger log4j = Logger.getLogger(Factory.class.getName());
	
	
	public Factory() {

	}

	public Factory(String resourceFileName) {
		try (FileInputStream fis = new FileInputStream(resourceFileName)) {
			propertyResourceBundle = new PropertyResourceBundle(fis);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void setDebugOn(){
		//log4j.setLevel(Level.DEBUG);
	}
	

	
	public void setPropertyResourceBundle(PropertyResourceBundle propertyResourceBundle){
		this.propertyResourceBundle = propertyResourceBundle;
	}
	
	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	/**
	 * 
	 * @param id
	 * @return 
	 */
	public abstract Bullet getBulletByID(int id);
	public abstract ArrayList<Bullet> getBullets();
	public  ArrayList<Bullet> getBulletsSortedOnDiameter(){
		ArrayList<Bullet> sortedList = new ArrayList<Bullet>();
		ArrayList<Bullet> unsortedList = getBullets();
		
		return sortedList;
	}
	public abstract void saveBullet(Bullet bullet) throws ReloadingException;

	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public abstract Cartridge getCartridgeByID(int id);
	public abstract ArrayList<Cartridge> getCartridges();
	public abstract void saveCartridge(Cartridge cartridge) throws ReloadingException;
	
	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public abstract Case getCaseByID(int id);
	public abstract ArrayList<Case> getCases();
	public abstract void saveCase(Case nCase) throws ReloadingException;
	
	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public abstract Firearm getFirearmByID(int id);
	public abstract ArrayList<Firearm> getFirearms();
	public abstract void saveFirearm(Firearm firearm) throws ReloadingException;

	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public abstract Load getLoadByID(int id);
	public abstract ArrayList<Load> getLoads();
	public abstract ArrayList<Load> getLoadsByCartridgeId(int cartridgeId);
	public abstract void saveLoad(Load load) throws ReloadingException;

	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public abstract Powder getPowderByID(int id);
	public abstract ArrayList<Powder> getPowders();
	public abstract void savePowder(Powder powder) throws ReloadingException;
	

	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public abstract Primer getPrimerByID(int id);
	public abstract ArrayList<Primer> getPrimers();
	public abstract void savePrimer(Primer primer) throws ReloadingException;
}
