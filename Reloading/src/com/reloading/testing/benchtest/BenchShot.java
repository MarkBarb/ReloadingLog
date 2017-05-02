package com.reloading.testing.benchtest;

import java.awt.geom.Point2D;

import com.reloading.testing.Shot;

public class BenchShot extends Shot {
	private int shotNumber = 0;
	private double velocity = 0.00;
	private Point2D point;
	boolean validImpact = true;
	
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

	public void setElevation(double elevation){
		this.point.setLocation(elevation, this.point.getY());
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
		this.point.setLocation(this.point.getX(), windage);
	}
	

	public String toString(){
		return Integer.toString(shotNumber);
	}
}
