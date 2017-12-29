package com.reloading.target;

import java.awt.geom.Point2D;

import com.reloading.testing.benchtest.BenchShot;

public class TargetEvaluatorShot extends BenchShot {
	

	private Point2D rawPoint = new Point2D.Double(0, 0);
	
	public TargetEvaluatorShot() {
		super(new Point2D.Double(0, 0));
		//this.rawPoint = new Point2D.Double(0, 0);
	}
	
	public TargetEvaluatorShot(Point2D rawPoint, Point2D convertedPoint) {
		super(convertedPoint);
		setRawPoint(rawPoint);
	}
	
	public Point2D getRawPoint() {
		return rawPoint;
	}
	
	public void setRawPoint(Point2D rawPoint) {
		this.rawPoint = rawPoint;
	}



	public double getRawElevation(){
		if (this.rawPoint == null ){
			return 0.0;
		}
		else {
			return this.rawPoint.getY();
		}
	}

	public void setRawElevation(double rawElevation){
		if (this.rawPoint == null ){
			this.rawPoint = new Point2D.Double( 0 , rawElevation );
		}
		else {
			this.rawPoint.setLocation(this.rawPoint.getX(), rawElevation);
		}
	}
	public double getRawWindage(){
		if (this.rawPoint == null ){
			return 0.0;
		}
		else {
			return this.rawPoint.getX();
		}
	}
	
	public void setRawWindage(double rawWindage){
		if (this.rawPoint == null ){
			this.rawPoint = new Point2D.Double(rawWindage, 0);
		}
		else {
			this.rawPoint.setLocation(rawWindage, this.rawPoint.getY() );
		}
	}
}
