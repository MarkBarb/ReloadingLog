package com.reloading.components;

import java.lang.reflect.Method;
import com.reloading.Constants;
public class Case extends Component  implements Comparable<Case> {

	private Cartridge cartridge = new Cartridge();
	private int cartridgeId = -1;

	
	public Case() {
		super();
	}
	
	public Case(int id, String manufacturer, String name) {
		super(id);
		this.manufacturer = manufacturer;
		this.name = name;
	}


	public int getCartridgeId() {
		if (cartridge == null){
			return cartridgeId;
		}
		else{
			return cartridge.getId();
		}
	}
	
	public Cartridge getCartridge() {
		return cartridge;
	}


	public void setCartridgeId(int cartridgeID) {
		this.cartridgeId = cartridgeID;
	}
	
	public void setCartridgeId(String cartridgeID) {
		this.cartridgeId = Integer.parseInt(cartridgeID);
	}

	public void setCartridge(Cartridge cartridge) {
		this.cartridge = cartridge;
	}

	
	public String toString(){
		if (cartridge == null){
			return shortName;
		}
		else {
			return cartridge.toString() + " " + manufacturer;
		}
	}

	@Override
	public int compareTo(Case nCase) {
		int compareVal = cartridge.compareTo(nCase.getCartridge());
		if (compareVal == 0){
			compareVal = shortName.compareTo(nCase.getShortName());
		}
		return compareVal;
	}

}
