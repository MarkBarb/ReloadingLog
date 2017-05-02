package com.reloading.target;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image; 
import java.awt.event.ComponentAdapter;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import com.reloading.browser.component.ComponentEvent;


public class TargetEvaluatorPanel extends JPanel {

	private Image targetImage;
	private static double aspectRatio = 1.0;
	private double zoom = 1.0;
	
	protected static TargetEvaluatorPanel makeTargetEvaluatorPanel(TargetEvaluatorAdaptor targetEvaluatorAdaptor
			,Image targetImage) {
		TargetEvaluatorPanel rtnPanel = new TargetEvaluatorPanel();
		
		return rtnPanel;
	}

	public TargetEvaluatorPanel() { 
		super();
	}
	public TargetEvaluatorPanel(TargetEvaluatorAdaptor targetEvaluatorAdaptor,Image targetImage) { 
		super(new GridBagLayout());
		
		System.out.println("Into TargetEvaluatorPanel Constructor");
		
		this.targetImage = targetImage; 
		aspectRatio = targetImage.getWidth(null)/ targetImage.getHeight(null);
		if (aspectRatio >= 1){
			zoom = TargetEvaluator.START_SIZE / targetImage.getWidth(null);
		}
		else{
			zoom = TargetEvaluator.START_SIZE / targetImage.getHeight(null);
		}
		
		//Dimension size = new Dimension((int)(zoom * targetImage.getWidth(null)), (int)(zoom * targetImage.getHeight(null)));
		Dimension size = new Dimension(TargetEvaluator.IMAGE_WIDTH, TargetEvaluator.IMAGE_HEIGHT);
		
		//targetImage.setSize();
		setPreferredSize(size); 
		setMinimumSize(size); 
		setMaximumSize(size); 
		setSize(size); 
		setLayout(null); 
		this.addMouseListener(targetEvaluatorAdaptor);
		System.out.println("exiting TargetEvaluatorPanel Constructor");

		
	} 
	
	//public void paintComponent(Graphics2D g) { 
		public void paintComponent(Graphics g) { 
		System.out.println(" TargetEvaluatorPanel.paintComponent ");
		
		/*Dimension size;
		if((double)(this.getWidth()/  this.getHeight()) >= aspectRatio){
			 size = new Dimension((int)(aspectRatio * this.getHeight()), this.getHeight()); 
		}
		else{
			size = new Dimension(this.getWidth(), (int)(this.getWidth()/aspectRatio));
		}
		*/
		//double xFactor = (double)(targetImage.getWidth(null)/this.getWidth());
		//double yFactor = (double)(this.getHeight()/targetImage.getHeight(null));
		//AffineTransform scaleTransformation = AffineTransform.getScaleInstance(xFactor, yFactor);
		 int newWidth = (int)(this.getHeight()/ aspectRatio  );
		 g.drawImage(targetImage,0,0,this.getWidth(),this.getHeight(),this);
		 //g.drawImage(targetImage,  scaleTransformation, null); 

	}
	
}

