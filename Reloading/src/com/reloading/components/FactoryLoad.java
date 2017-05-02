package com.reloading.components;

public class FactoryLoad extends Load {
	public FactoryLoad() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public FactoryLoad(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public FactoryLoad (int id, String manufacturer, String name) {
		super(id);
		this.manufacturer = manufacturer;
		this.name = name;
	}
	
}
