package com.reloading.components;

public class Primer extends Component {

	public Primer() {
		super();
	}
	public Primer(int id, String manufacturer, String name) {
		super(id);
		this.manufacturer = manufacturer;
		this.name = name;
	}
	
}
