package com.reloading.components;

public class Cartridge extends Component implements Comparable<Cartridge>{
	
	private float nominalSize = 0.0f;

	public Cartridge() {
		super();
	}
	public Cartridge(int id, String name) {
		super(id);
		this.name = name;
	}

	public Cartridge(String name,String shortName) {
		super(-1);
		this.name = name;
		this.shortName = shortName;
	}



	public float getNominalSize() {
		return nominalSize;
	}
	
	public String getNominalSizeString() {
		return Float.toString(nominalSize);
	}
	
	public void setNominalSize(float nominalSize) {
		this.nominalSize = nominalSize;
	}
	
	public void setNominalSize(String nominalSize) {
		this.nominalSize = Float.parseFloat(nominalSize);
	}
	
	@Override
	public String toString() {
		return  shortName ;
	}
	
	@Override
    public int compareTo(Cartridge cartridge) {
		
		if (nominalSize < cartridge.getNominalSize()){ 
			return -1;
		}
		else if(nominalSize > cartridge.getNominalSize()){ 
			return 1;
		}
		return shortName.compareTo(cartridge.getShortName());
    }
}
