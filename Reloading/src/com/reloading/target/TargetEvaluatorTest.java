package com.reloading.target;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.reloading.testing.benchtest.BenchTest;

public class TargetEvaluatorTest extends BenchTest {
	
	private double witnessX = 7.5;
	private double witnessY = 3.375;
	
	private Point2D witnessUpperLeft = new Point2D.Double(0, 0); 
	private Point2D witnessUpperRight = new Point2D.Double(0, 500); 
	private Point2D witnessLowerLeft = new Point2D.Double(500, 0);
	private Point2D sightPoint = new Point2D.Double(250, 250);
	private String scannedTarget = "";
	

	public TargetEvaluatorTest() {
		super();
	}
	
	public String getScannedTarget() {
		return scannedTarget;
	}

	public void setScannedTarget(String scannedTarget) {
		this.scannedTarget = scannedTarget;
	}


	/*********************************************************/
	/*                                                       */
	/*            Sight Point                                */
	/*                                                       */
	/*********************************************************/
	public Point2D getSightPoint() {
		return sightPoint;
	}

	public double getSightPointX() {
		return sightPoint.getX();
	}
	
	public double getSightPointY() {
		return sightPoint.getY();
	}
	
	public void setSightPoint(Point2D sightPoint) {
		this.sightPoint = sightPoint;
	}
	
	public void setSightPointX(double sightPointX) {
		this.sightPoint.setLocation(sightPointX, this.sightPoint.getY()); 
	}
	
	public void setSightPointY(double sightPointY) {
		this.sightPoint.setLocation(this.sightPoint.getX() ,sightPointY ); 
	}
	

	/*********************************************************/
	/*                                                       */
	/*            Lower Left Witness Point                   */
	/*                                                       */
	/*********************************************************/
	public Point2D getWitnessLowerLeft() {
		return witnessLowerLeft;
	}
	
	public double getWitnessLowerLeftX() {
		return witnessLowerLeft.getX();
	}
	
	public double getWitnessLowerLeftY() {
		return witnessLowerLeft.getY();
	}
	
	public void setWitnessLowerLeft(Point2D witnessLowerLeft) {
		System.out.println("setWitnessLowerLeft");
		this.witnessLowerLeft = witnessLowerLeft;
	}
	
	public void setWitnessLowerLeftX(double witnessUpperLeftX) {
		this.witnessLowerLeft.setLocation(witnessUpperLeftX, this.witnessLowerLeft.getY());
	}
	
	public void setWitnessLowerLeftY(double witnessUpperLeftY) {
		this.witnessLowerLeft.setLocation(this.witnessLowerLeft.getX(),witnessUpperLeftY );
	}
	
	/*********************************************************/
	/*                                                       */
	/*            Upper Left Witness Point                   */
	/*                                                       */
	/*********************************************************/
	
	public Point2D getWitnessUpperLeft() {
		return witnessUpperLeft;
	}

	public double getWitnessUpperLeftX() {
		return witnessUpperLeft.getX();
	}
	
	public double getWitnessUpperLeftY() {
		return witnessUpperLeft.getY();
	}
	
	public void setWitnessUpperLeft(Point2D witnessUpperLeft) {
		this.witnessUpperLeft = witnessUpperLeft;
	}
	
	public void setWitnessUpperLeftX(double witnessUpperLeftX) {
		this.witnessUpperLeft.setLocation(witnessUpperLeftX, this.witnessUpperLeft.getY());
	}
	
	public void setWitnessUpperLeftY(double witnessUpperLeftY) {
		this.witnessUpperLeft.setLocation(this.witnessUpperLeft.getX(), witnessUpperLeftY );
	}


	
	/*********************************************************/
	/*                                                       */
	/*            Upper Right Witness Point                  */
	/*                                                       */
	/*********************************************************/

	public Point2D getWitnessUpperRight() {
		return witnessUpperRight;
	}

	public double getWitnessUpperRightX() {
		return witnessUpperRight.getX();
	}
	
	public double getWitnessUpperRightY() {
		return witnessUpperRight.getY();
	}
	
	public void setWitnessUpperRight(Point2D witnessUpperRight) {
		this.witnessUpperRight = witnessUpperRight;
	}
	
	public void setWitnessUpperRightX(double witnessUpperRightX) {
		this.witnessUpperRight.setLocation(witnessUpperRightX,this.witnessUpperRight.getY());
	}
	
	public void setWitnessUpperRightY(double witnessUpperRightY) {
		this.witnessUpperRight.setLocation(this.witnessUpperRight.getX(),witnessUpperRightY);
	}


	/*********************************************************/
	/*                                                       */
	/*            Dimension of witness marks                 */
	/*                                                       */
	/*********************************************************/

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
		this.witnessY = witnessY;
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
	
	public void recalculateAllPoints(){
		//System.out.println("recalculateAllPoints");
		for(int index=0;index<shots.size();index++){
			TargetEvaluatorShot shot = (TargetEvaluatorShot) shots.get(index);
			Point2D rawPoint = shot.getRawPoint();
			System.out.println("RawPt: " + rawPoint.getX() + "," + rawPoint.getY());
			Point2D calculatedPoint = calculate(rawPoint);
			System.out.println("calculatedPoint: " + calculatedPoint.getX() + "," + calculatedPoint.getY());
			shot.setPoint(calculatedPoint);
		}
	}
	
	public boolean addShotPoint(Point2D shotPoint){
		//System.out.println("addShotPoint");
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
