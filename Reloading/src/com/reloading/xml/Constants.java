package com.reloading.xml;

public class Constants {

	//attributes common to all components
	public static final String CLASSNAME = "ClassName";
	public static final String ID = "Id";
	public static final String MANUFACTURER = "Manufacturer";
	public static final String NAME = "Name";
	public static final String SHORTNAME = "ShortName";
	
	//Bullet node names and attributes
	public static final String BULLET = "Bullet";
	public static final String BULLETS = "Bullets";
	protected static final String[] BULLET_ATTRIBUTES = {
			"BallisticCoeffient"
			,"Diameter"
			,"Style"
			,"Weight"
	};
	protected static final String[] CASTBULLET_ATTRIBUTES = {
			"Alloy"
			,"BallisticCoeffient"
			,"Diameter"
			,"GasChecked"
			,"Lube"
			,"SizeTo"
			,"Style"
			,"Weight"
	};

	//Cases node names and attributes
	public static final String CASES = "Cases";
	public static final String CASE = "Case";
	protected static final String[] CASE_ATTRIBUTES = {
	};
	
	//Cartridge node names and attributes
	public static final String CARTRIDGE = "Cartridge";
	public static final String CARTRIDGES = "Cartridges";
	protected static final String[] CARTRIDGE_ATTRIBUTES = {
	};

	//Powder node names and attributes
	public static final String POWDER = "Powder";
	public static final String POWDERS = "Powders";
	protected static final String[] POWDER_ATTRIBUTES = {
	};
	
	//attributes for bullets
	public static final String ALLOY = "Alloy";
	public static final String DIAMETER = "Diameter";
	public static final String GASCHECKED = "GasChecked";
	public static final String LUBE = "Lube";
	public static final String SIZE_TO = "SizeTo";
	public static final String STYLE = "Style";
	public static final String WEIGHT = "Weight";
	
	public Constants() {
		// TODO Auto-generated constructor stub
	}

}
