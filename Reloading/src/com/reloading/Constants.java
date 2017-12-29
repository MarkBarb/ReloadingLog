package com.reloading;

public class Constants {
	//Size of main frame
	public static final int LOG_WIDTH = 800;
	public static final int LOG_HEIGHT = 800;
	
	//attributes common to all components
	public static final String CLASSNAME = "ClassName";
	public static final String ID = "Id";
	public static final String MANUFACTURER = "Manufacturer";
	public static final String NAME = "Name";
	public static final String SHORTNAME = "ShortName";
	
	//Bullet node names and attributes
	public static final String BULLET_CLASSNAME = "com.reloading.components.Bullet";
	public static final String BULLET = "Bullet";
	public static final String BULLETS = "Bullets";
	public static final String[] BULLET_ATTRIBUTES = {
			"BallisticCoeffient"
			,"Diameter"
			,"Style"
			,"Weight"
	};
	public static final String[] BULLET_COLUMNS= {
			"Display Name"
			,"BallisticCoeffient"
			,"Diameter"
			,"Style"
			,"Weight"
	};
	public static final String CASTBULLET_CLASSNAME = "com.reloading.components.CastBullet";
	public static final String[] CASTBULLET_ATTRIBUTES = {
			"Alloy"
			,"BallisticCoeffient"
			,"Diameter"
			,"GasChecked"
			,"Lube"
			,"AsCastDiameter"
			,"Style"
			,"Weight"
	};

	//Cases node names and attributes
	public static final String CASES = "Cases";
	public static final String CASE = "Casing";
	public static final String[] CASE_ATTRIBUTES = {
			"CartridgeId"
	};
	public static final String[] CASE_COLUMNS= {
			"Display Name"
			,"Name"
			,"Manufacturer"
	};
	
	//Cartridge node names and attributes
	public static final String CARTRIDGE = "Cartridge";
	public static final String CARTRIDGEID = "CartridgeId";
	public static final String CARTRIDGES = "Cartridges";
	public static final String[] CARTRIDGE_ATTRIBUTES = {
			"NominalSize"
	};
	public static final String[] CARTRIDGE_COLUMNS= {
			"Display Name"
			,"Cartridge"
	};

	//Firearm node names and attributes
	public static final String FIREARM = "Firearm";
	public static final String FIREARMS = "Firearms";
	public static final String[] FIREARM_ATTRIBUTES = {
			"ShortName"
			,"Name"
			,"Model"
			,"Serial"
	};
	public static final String[] FIREARM_COLUMNS= {
			"Firearm"
			,"Manufacturer"
			,"Model"
			,"Serial Number"
	};
	//Load node names and attributes
	public static final String LOAD = "Load";
	public static final String LOADS = "Loads";
	public static final String FACTORYLOAD_CLASSNAME = "com.reloading.components.FactoryLoad";
	public static final String[] FACTORYLOAD_COLUMNS= {
			"Factory"
			,"Cartridge"
			,"Manufacturer"
			,"Name"
			,"Comments"
			
	};
	
	public static final String[] FACTORYLOAD_ATTRIBUTES = {
			"CartridgeId"
			,"Manufacturer"
			,"Name"
			,"ShortName"
			,"Comments"
	};
	
	public static final String RELOAD_CLASSNAME = "com.reloading.components.Reload";
	public static final String[] RELOAD_ATTRIBUTES = {
			"BulletId"
			,"CartridgeId"
			,"CaseId"
			,"Comments"
			,"OverAllLength"
			,"PowderCharge"
			,"PowderId"
			,"PrimerId"
	};
	
	public static final String[] RELOAD_COLUMNS= {
			"Cartridge"
			,"Bullet"
			,"Powder"
			,"PowderMeasureSetting"
			,"Primer"
			,"Case"
			,"OverAllLength"
			,"Comment"
			
	};
	public static final String[] RELOAD_COLUMNS_old= {
			"Reload"
			,"Cartridge"
			,"Bullet"
			,"Powder"
			,"PowderMeasureSetting"
			,"Primer"
			,"Case"
			,"OverAllLength"
			
	};

	//Powder node names and attributes
	public static final String POWDER = "Powder";
	public static final String POWDERS = "Powders";
	public static final String[] POWDER_ATTRIBUTES = {
	};
	public static final String[] POWDER_COLUMNS= {
			"Display Name"
			,"Name"
			,"Manufacturer"
	};

	//Primer node names and attributes
	public static final String PRIMER = "Primer";
	public static final String PRIMERS = "Primers";
	public static final String[] PRIMER_ATTRIBUTES = {
	};
	public static final String[] PRIMER_COLUMNS= {
			"Display Name"
			,"Name"
			,"Manufacturer"
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
