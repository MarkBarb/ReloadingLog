package com.reloading.components;

public class JacketedBullet extends Bullet {
	
	public JacketedBullet(){
		super();
	}
	
	public JacketedBullet(int id) {
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
}
