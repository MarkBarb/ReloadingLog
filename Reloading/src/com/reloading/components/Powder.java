package com.reloading.components;

public class Powder extends Component implements Comparable<Powder> {

	public Powder() {
		super();
	}
	
	public Powder(int id, String manufacturer, String name) {
		super(id);
		this.manufacturer = manufacturer;
		this.name = name;
	}

	@Override
	public int compareTo(Powder powder) {
		int compareValue = 0;
		compareValue = manufacturer.compareTo(powder.getManufacturer());
		if (compareValue == 0){
			compareValue = name.compareTo(powder.getName());
		}
		return compareValue;
	}
	
}
