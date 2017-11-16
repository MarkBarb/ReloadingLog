package com.reloading.components;

/**
 * This defines the actual projectile and not a loaded round.
 * I am used to english units, but there is no requirement of that
 * for this project. Be consistent though.  Sorting is done using
 * diameter and weight. If you enter the diameter of a 9mm as 9 instead of .356. It will
 * sort after a .44 bullet entered as .429.
 * @author Mark Barb
 *
 */
public class Bullet extends Component implements Comparable<Bullet>{

	public Bullet(){
		super();
	}
	
	public Bullet(int id){
		super(id);
	}
	
	private float ballisticCoefficient;
	private float diameter = 0.0f;
	private String style = "";
	private float weight = 0.0f;
	
	/**
	 * Returns the Ballistic Coefficient 
	 * @return
	 */
	public float getBallisticCoefficient() {
		return ballisticCoefficient;
	}
	/**
	 * Returns the Ballistic Coefficient 
	 * @return
	 */
	//public float getBallisticCoefficient() {
	//	return ballisticCoefficient;
	//}

	/**
	 * Returns a String representation of the Ballistic Coefficient 
	 * @return
	 */
	//public String getBallisticCoefficient() {
	//	return String.valueOf(ballisticCoefficient);
	//}
	/**
	 * Returns a String representation of the Ballistic Coefficient 
	 * @return
	 */
	public String getBallisticCoefficientString() {
		return String.valueOf(ballisticCoefficient);
	}

	/**
	 * Returns the diameter of the bullet
	 * @return
	 */
	public float getDiameter() {
		return diameter;
	}
	
	/**
	 * Returns a String Representation of the diameter of the bullet
	 * @return
	 */
	public String getDiameterString() {
		return String.valueOf(diameter);
	}

	/**
	 * Returns the style of the bullet.
	 * @return
	 */
	public String getStyle() {
		return style;
	}
	
	/**
	 * Returns the weight of the bullet, generally in grains.
	 * @return
	 */
	public float getWeight() {
		return weight;
	}
	
	/**
	 * Returns a String value of the weight of the bullet, generally in grains.
	 * @return
	 */
	public String getWeightString() {
		return String.valueOf(weight);
	}

	/**
	 * Sets the ballistic coefficient 
	 * @param ballisticCoefficient
	 */
	public void setBallisticCoefficient(float ballisticCoefficient) {
		this.ballisticCoefficient = ballisticCoefficient;
	}
	
	/**
	 * Sets the ballistic coefficient 
	 * @param ballisticCoefficient
	 */
	public void setBallisticCoefficient(String ballisticCoefficient){
		this.ballisticCoefficient = Float.parseFloat(ballisticCoefficient);
	}



	/**
	 * Sets the diameter of the bullet. 
	 * I am used to english units, but there is no requirement of that
	 * for this project. Be consistent though.  Sorting is done using
	 * diameter and weight. If you enter the diameter of a 9mm as 9 instead of .356. It will
	 * sort after a .44 bullet entered as .429.
	 * @param diameter
	 */
	public void setDiameter(String diameter){
		this.diameter = Float.parseFloat(diameter);
	}
	/**
	 * Sets the diameter of the bullet. 
	 * I am used to english units, but there is no requirement of that
	 * for this project. Be consistent though.  Sorting is done using
	 * diameter and weight. If you enter the diameter of a 9mm as 9 instead of .356. It will
	 * sort after a .44 bullet entered as .429.
	 * @param diameter
	 */
	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}

	/**
	 * Sets the style of the bullet.
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Sets the weight of the bullet.
	 * I am used to using grains, but there is no requirement of that
	 * for this project. Be consistent though.
	 * @param weight
	 */
	public void setWeight(String weight) {
		this.weight = Float.parseFloat(weight);
	}

	/**
	 * Sets the weight of the bullet.
	 * I am used to using grains, but there is no requirement of that
	 * for this project. Be consistent though.
	 * @param weight
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		if (shortName.length() > 0){
			return getShortName();
		}
		String shortMaker;
		if (manufacturer.length() > 5){
			shortMaker = manufacturer.substring(0, 3).toUpperCase();
		}
		else{
			shortMaker = manufacturer;
		}
	
		return shortMaker + " " + diameter + " " + String.format("%.0f",weight) + "gr " + style;
	}
	@Override
	/**
	 * Returns 	-1 if diameter of second bullet is greater than bullet doing comparing.
	 * 			 1 if diameter of second bullet is less than bullet doing comparing.
	 * else Returns
	 * 			-1 if weight of second bullet is greater than bullet doing comparing.
	 * 			 1 if weight of second bullet is less than bullet doing comparing.
	 * else Returns shortname comparison
	 */
    public int compareTo(Bullet bullet) {
        if (diameter > bullet.getDiameter()){
        	return 1;
        }
        else if(bullet.getDiameter() == diameter){
        	if (weight > bullet.getWeight()){
        		return 1;
        	}
        	else if(weight < bullet.getWeight()){
        		return -1;
        	}
        	else {
        		return shortName.compareTo(bullet.getShortName());	
        	}
        }
        else{
        	return -1;
        }
    }
	
}
