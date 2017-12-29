package com.reloading.testing.benchtest;

import java.awt.geom.Point2D;

import com.reloading.components.Firearm;
import com.reloading.components.Load;
import com.reloading.testing.Shot;
import com.reloading.testing.Test;

public class BenchShot extends Shot implements Comparable<Shot>{
	private int shotNumber = 0;
	private double velocity = 0.00;
	private Point2D point;
	boolean validImpact = true;

	public BenchShot() {
		super();
		this.velocity = -1;
		this.point = new Point2D.Double(0,0);
		
	}

	public BenchShot(double velocity) {
		super();
		this.velocity = velocity;
	}

	public BenchShot(double elevation, double windage) {
		super();
		Point2D.Double point = new Point2D.Double(elevation,windage);
		this.point = point;
	}
	
	public BenchShot(Point2D point) {
		super();
		this.point = point;
	}

	public BenchShot(double velocity, Point2D.Double point) {
		super();
		this.velocity = velocity;
		this.point = point;
	}
	

	public BenchShot(double velocity, double elevation, double windage) {
		super();
		this.velocity = velocity;
		Point2D.Double point = new Point2D.Double(elevation,windage);
		this.point = point;
		
	}

	public int getShotNumber() {
		return shotNumber;
	}

	public void setShotNumber(int shotNumber) {
		this.shotNumber = shotNumber;
	}
	
	
	public double getElevation(){
		return point.getY();
	}

	public Point2D getPoint() {
		return point;
	}
	
	public double getVelocity() {
		return velocity;
	}
	public double getWindage(){
		return point.getX();
	}
	public boolean hasValidImpact() {
		return validImpact;
	}

	public boolean isValidImpact() {
		return validImpact;
	}
	public void setElevation(double elevation){
		if (this.point == null){
			this.point= new Point2D.Double(0.0, elevation);
		}
		else{
			this.point.setLocation(this.point.getX(),elevation );
		}
		
	}

	public void setPoint(Point2D point) {
		this.point = point;
	}

	public void setValidImpact(boolean validImpact) {
		this.validImpact = validImpact;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public void setWindage(double windage){
		if (this.point == null ){
			this.point = new Point2D.Double(windage, 0);
		}
		else {
			this.point.setLocation(windage, this.point.getY() );
		}
	}
	

	public String toString(){
		return Integer.toString(shotNumber);
	}
	

    public int compareTo(Shot shot) {
    	if (shot instanceof BenchShot){
    		return this.compareTo((BenchShot)shot);
    	}
    	
    	Shot tempShot = (Shot) this;
    	return tempShot.compareTo(shot);
    
    }
    public int compareTo(BenchShot shot) {
		int compShotNum = shot.getShotNumber();
		if (shotNumber < compShotNum ){
			return -1;
		}
		else if (shotNumber > compShotNum ){
			return 1;
		}
		
		int compId = shot.getId();
		int thisId = this.getId();
		if (thisId < compId ){
			return -1;
		}
		else if (thisId > compId ){
			return 1;
		}
		return 0;
    }
}
