package com.reloading.components;

public class CastBullet extends Bullet {
	private String alloy = "";
	private boolean gasChecked=false;
	private String lube="";
	private float asCastDiameter = 0.0f;
	
	public CastBullet(){
		super();
	}
	public CastBullet(int id) {
		super(id);
	}
	
	/**
	 * Sets the ballistic coefficient 
	 * @param ballisticCoefficient
	 */
	public void setBallisticCoefficient(float ballisticCoefficient) {
		super.setBallisticCoefficient( ballisticCoefficient);
		//this.ballisticCoefficient = ballisticCoefficient;
	}
	
	public String getAlloy() {
		return alloy;
	}

	public boolean isGasChecked() {
		return gasChecked;
	}
	
	public int getGasCheck() {
		if (gasChecked){
			return 1;
		}
		return 0;
	}
	
	public String getGasCheckedString(){
		if(gasChecked){
			return "true";
		}
		return "false";
	}

	public String getLube() {
		return lube;
	}

	public float getAsCastDiameter() {
		return asCastDiameter;
	}

	public void setAlloy(String alloy) {
		this.alloy = alloy;
	}
	
	public void setGasChecked(String gasChecked){
		this.gasChecked = Boolean.parseBoolean(gasChecked);
	}

	public void setGasCheck(int gasChecked) {

		this.gasChecked = false;
		if (gasChecked > 0){
			this.gasChecked = true;
		}
	}

	
	public void setGasChecked(boolean gasChecked) {
		this.gasChecked = gasChecked;
	}

	public void setLube(String lube) {
		this.lube = lube;
	}

	public void setAsCastDiameter(float asCastDiameter) {
		this.asCastDiameter = asCastDiameter;
	}

	public void setAsCastDiameter(Float asCastDiameter) {
		this.asCastDiameter = asCastDiameter;
	}

	public void setAsCastDiameter(String asCastDiameter) {
		this.asCastDiameter = Float.parseFloat(asCastDiameter);
	}
	
	public void setSizeTo(float asCastDiameter) {
		this.asCastDiameter = asCastDiameter;
	}

	
}
