package com.reloading.target;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.reloading.testing.benchtest.BenchTest;

public class TargetEvaluatorTest extends BenchTest {
	private Point2D witnessUpperLeft;
	private Point2D witnessUpperRight;
	private Point2D witnessLowerLeft;
	private Point2D sightPoint;
	private String scannedTarget = "";
	
	
	public String getScannedTarget() {
		return scannedTarget;
	}

	public void setScannedTarget(String scannedTarget) {
		this.scannedTarget = scannedTarget;
	}

	public Point2D getSightPoint() {
		return sightPoint;
	}

	public void setSightPoint(Point2D sightPoint) {
		System.out.println("setSightPoint");
		this.sightPoint = sightPoint;
	}

	public Point2D getWitnessLowerLeft() {
		return witnessLowerLeft;
	}

	//private double witnessX = 8.5;
	//private double witnessY = 11.0;
	
	private double witnessX = 7.5;
	private double witnessY = 3.375;
	
	
	public Point2D getWitnessUpperLeft() {
		return witnessUpperLeft;
	}

	public void setWitnessUpperLeft(Point2D witnessUpperLeft) {
		System.out.println("setWitnessUpperLeft");
		this.witnessUpperLeft = witnessUpperLeft;
	}

	public Point2D getWitnessUpperRight() {
		return witnessUpperRight;
	}

	public void setWitnessUpperRight(Point2D witnessUpperRight) {
		System.out.println("setWitnessUpperRight");
		this.witnessUpperRight = witnessUpperRight;
	}
//TODO: ERROR HANDLING DIVIDE BY ZERO, NULL
	public Point2D calculate(Point2D rawPoint){
		double xScale = witnessX / (witnessUpperLeft.getX() - witnessUpperRight.getX());
		double yScale = witnessY / (witnessLowerLeft.getY() - witnessUpperLeft.getY());
		double x = 0.0;
		double y = 0.0;
		//Point2D rawPoint = shot.getRawPoint();
		x = xScale * ((double)sightPoint.getX() - (double)rawPoint.getX());
		y = yScale * ((double)sightPoint.getY() - (double)rawPoint.getY());
			
		Point2D.Double calculatedPoint = new Point2D.Double(x,y);
		
		return calculatedPoint;
	}
	
	protected void recalculateAllPoints(){
		System.out.println("recalculateAllPoints");
		for(int index=0;index<shots.size();index++){
			TargetEvaluatorShot shot = (TargetEvaluatorShot) shots.get(index);
			Point2D rawPoint = shot.getRawPoint();
			System.out.println("RawPt: " + rawPoint.getX() + "," + rawPoint.getY());
			Point2D calculatedPoint = calculate(rawPoint);
			System.out.println("calculatedPoint: " + calculatedPoint.getX() + "," + calculatedPoint.getY());
			shot.setPoint(calculatedPoint);
		}
	}
	public void setWitnessLowerLeft(Point2D witnessLowerLeft) {
		System.out.println("setWitnessLowerLeft");
		this.witnessLowerLeft = witnessLowerLeft;
	}

	public double getWitnessX() {
		return witnessX;
	}

	public void setWitnessX(double witnessX) {
		this.witnessX = witnessX;
	}

	public double getWitnessY() {
		return witnessY;
	}

	public void setWitnessY(double witnessY) {
		System.out.println("setWitnessY( " +witnessY + ")");
		this.witnessY = witnessY;
	}

	public TargetEvaluatorTest() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean addShotPoint(Point2D shotPoint){
		System.out.println("addShotPoint");
		int shotNumber = shots.size() + 1;
		Point2D convertedPoint = calculate(shotPoint);
		TargetEvaluatorShot teShot = new TargetEvaluatorShot(shotPoint,convertedPoint);
		teShot.setId(shotNumber);
		teShot.setShotNumber(shotNumber);
		//teShot.setVelocity(velocity);
		addShot(teShot);
		return true;
	}
}
