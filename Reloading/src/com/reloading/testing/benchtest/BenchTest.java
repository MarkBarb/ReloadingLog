package com.reloading.testing.benchtest;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;




import com.reloading.components.Firearm;
import com.reloading.testing.Shot;
import com.reloading.testing.Test;

public class BenchTest extends Test{
	
	public BenchTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Calculates averageVelocity
	 * @return
	 */
	public double getAverageVelocity(){
		double velocity = 0.0;
		double aveVelocity = 0;
		int shotCount = 0;
		for (int outerIdx = 0;outerIdx < shots.size();outerIdx++){
			BenchShot currentShot = (BenchShot) getShot(outerIdx);
			if (currentShot.getVelocity() > 0){
				velocity+= currentShot.getVelocity();
				shotCount++;
			}
		}
		if (shotCount > 0) {
			aveVelocity = velocity/shots.size();
		}
		return aveVelocity;
	}
	
	public double getElevationSD(){
		double sd = 0.0;
		double diffSum = 0;
		double n = 0.0;
		//double aveElevation = getGroupCenter().getX();
		double aveElevation = getGroupCenter().getY();
		for (int outerIdx = 0;outerIdx < shots.size();outerIdx++){
			
			BenchShot currentShot = (BenchShot) getShot(outerIdx);
			if (currentShot.hasValidImpact()){
				diffSum += Math.pow((currentShot.getElevation() - aveElevation), 2);
				n+= 1.0;
			}
		}
		if (n > 1.0){
			sd = Math.sqrt(diffSum/(n - 1.0));
		}
		return sd;
	}
	
	
	public double getWindageSD(){
		double sd = 0.0;
		double diffSum = 0;
		double n = 0.0;
		//double aveWindage = getGroupCenter().getY();
		double aveWindage = getGroupCenter().getX();
		for (int outerIdx = 0;outerIdx < shots.size();outerIdx++){
			BenchShot currentShot = (BenchShot) getShot(outerIdx);
			if (currentShot.hasValidImpact()){
				diffSum += Math.pow((currentShot.getWindage() - aveWindage), 2);
				n+= 1.0;
			}
		}
		if (n > 1.0){
			sd = Math.sqrt(diffSum/(n - 1.0));
		}
		return sd;
	}
	/**
	 * Calculates the center of the group.
	 * @return
	 */
	public Point2D getGroupCenter(){
		Point2D.Double center = new Point2D.Double();
		double x = 0;
		double y = 0;
		int shotIdx;
		for (shotIdx=0;shotIdx< shots.size();shotIdx++){
			BenchShot compShot = (BenchShot) getShot(shotIdx);
			Point2D currentPoint = compShot.getPoint();
			x += currentPoint.getX();
			y += currentPoint.getY();
		}
		if (shotIdx > 0 ){
			center.setLocation(x/shotIdx, y/shotIdx );
		}
		else {
			center.setLocation(0,0);
		}
		
		return center;
	}
	/**
	 * Calculates size of group
	 * @return
	 */
	public double getGroupSize(){
		double groupSize = -1.0;
		double tmpGroupSize = 0.0;
		ArrayList<Shot> shots = getShotsList();
		
		for (int outerIdx = 0;outerIdx < shots.size();outerIdx++){
			BenchShot currentShot = (BenchShot) getShot(outerIdx);
			Point2D currentPoint = currentShot.getPoint();
			if (currentPoint == null){
				continue;
			}
			for (int innerIdx=outerIdx+1;innerIdx< shots.size();innerIdx++){
				BenchShot compShot = (BenchShot) getShot(innerIdx);
				tmpGroupSize = currentPoint.distance(compShot.getPoint());
				if (tmpGroupSize >= groupSize){
					groupSize = tmpGroupSize;
				}
			}
		}
		return groupSize;
	}

	public double getVelocitySD(){
		double sd = 0.0;
		double diffSum = 0;
		double n = 0.0;
		double aveVelocity = getAverageVelocity();
		for (int outerIdx = 0;outerIdx < shots.size();outerIdx++){
			BenchShot currentShot = (BenchShot) getShot(outerIdx);
			if (currentShot.getVelocity()> 0){
				diffSum += Math.pow((currentShot.getVelocity() - aveVelocity), 2);
				n+= 1.0;
			}
		}
		if (n > 1.0){
			sd = Math.sqrt(diffSum/(n - 1.0));
		}
		return sd;
	}

	  public static void main(String args[]) {
		   double x=0;
		   double xOffset=-.1;
		   double y=0;
		   double yOffset = -.25;
		   double velocity=0;
		   double vOffset = -2.0;
		   Firearm fireArm = new Firearm(1,"Browning","Buckmark");
		   BenchTest benchTest = new BenchTest();
		  /* for (int idx = 0;idx< 5;idx++){
			   x=xOffset;
			   for (int xIdx = 0;xIdx < idx;xIdx++){
				   x*= xOffset;
			   }
			   x += 1;
			   y=yOffset;
			   for (int xIdx = 0;xIdx < idx;xIdx++){
				   y*= yOffset;
			   }
			   y += 1;
			   
			   velocity = vOffset;
			   for (int xIdx = 0;xIdx < idx;xIdx++){
				   velocity *= vOffset;
			   }
			   velocity += 1250;
			   
			   BenchShot shot = new BenchShot(velocity,x,y);
			   System.out.println("Shot: v: " + shot.getVelocity() 
					   + "\t x: " + x 
					   + "\t y: " + y);
			   benchTest.addShot(shot);
		   }*/
		   BenchShot shot1 = new BenchShot(0,1.25,-1.625);
		   benchTest.addShot(shot1);
		   
		   BenchShot shot2 = new BenchShot(0,1.325,.625);
		   benchTest.addShot(shot2);
		   
		   BenchShot shot3 = new BenchShot(0,3,-1);
		   benchTest.addShot(shot3);
		   
		   BenchShot shot4 = new BenchShot(0,3.25,-.75);
		   benchTest.addShot(shot4);
		   
		   System.out.println("GroupSize: " + benchTest.getGroupSize());
		   
		   Point2D point = benchTest.getGroupCenter();
		   System.out.println("GroupCenter: elevation: " + point.getX() + " Windage: " + point.getY());
		   System.out.println("Standard Deviation of elevation: " + benchTest.getElevationSD());
		   System.out.println("Standard Deviation of windage: " + benchTest.getWindageSD());
		   
		   //System.out.println("\nAve Velocity: " + benchTest.getAverageVelocity());
		   //System.out.println("Standard Deviation of Velocity: " + benchTest.getVelocitySD());
		   //System.out.println("Standard Deviation of elevation: " + benchTest.getElevationSD());
		   //System.out.println("Standard Deviation of windage: " + benchTest.getWindageSD());
		   
		   
	  }
	
}
