package com.reloading.target;

import java.awt.geom.Point2D;

import com.reloading.testing.benchtest.BenchShot;

public class TargetEvaluatorShot extends BenchShot {
	

	private Point2D rawPoint;
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


}
