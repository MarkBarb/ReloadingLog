package com.reloading.components;

import java.util.ArrayList;

import com.reloading.testing.Test;

public class Firearm extends Component implements Comparable<Firearm>{
	private String model = "";
	private String serial = "";
	
	private ArrayList<String> imageFileNames = new ArrayList<String>();

	public Firearm() {
		super();
	}
	
	public Firearm(int id, String manufacturer, String model) {
		super(id);
		this.manufacturer = manufacturer;
		this.model = model;
	}

	public Firearm(int id, String manufacturer, String model, String serial) {
		super(id);
		this.manufacturer = manufacturer;
		this.model = model;
		this.serial = serial;
	}

	public void addImageFileName(String imageFileName){
		if (imageFileNames == null){
			imageFileNames = new ArrayList<String>(); 
		}
		for (int index=0;index<imageFileNames.size();index++){
			if (imageFileName.compareTo(imageFileNames.get(index)) == 0){
				return;
			}
		}
		imageFileNames.add(imageFileName);
	}
	public String getImageFileName(int index) {
		String rtnString = "";
		if (imageFileNames != null && index < imageFileNames.size()){
			rtnString = imageFileNames.get(index); 
		}
		return rtnString;
	}
	
	public ArrayList<String> getImageFileNames() {
		return imageFileNames;
	}



	public String getModel() {
		return model;
	}

	public String getSerial() {
		return serial;
	}
	
	

	public void setModel(String model) {
		this.model = model;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	
	@Override
	public String toString(){
		if (shortName.length()> 0){
			return shortName;
		}
		String str = manufacturer.substring(0, 2) + model;
		return str;
	}

	@Override
    public int compareTo(Firearm firearm) {
		int rtnVal = 0;
		//compare the manufacturer
		String compareString = firearm.getManufacturer();
		rtnVal = manufacturer.compareToIgnoreCase(compareString);
		if (rtnVal != 0) return rtnVal;
		
		//compare the model
		compareString = firearm.getModel();
		rtnVal = model.compareToIgnoreCase(compareString);
		if (rtnVal != 0) return rtnVal;
		
		//compare serial numbers
		compareString = firearm.getSerial();
		rtnVal = serial.compareToIgnoreCase(compareString);
		if (rtnVal != 0) return rtnVal;
		
		return rtnVal;
    }
	
}
