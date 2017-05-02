package com.reloading.browser.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ComponentHandler extends MouseAdapter implements ActionListener {
	private ComponentPanel changeServerPanel;
	//What do do if the
	protected ComponentHandler(ComponentPanel changeServerPanel){
		super();
		this.changeServerPanel = changeServerPanel;
		
	}
	/**
	 * Handles Mouse Events.
	 */
	public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
			System.out.println("DoubleClick ");
        	doIt();
        }
      }
	
	/** 
	 * Handles actionEvents for the panel
	 */
	public void actionPerformed(ActionEvent e) {        
		String actionCommand = e.getActionCommand();
		switch (actionCommand){
		case ComponentPanel.HELP:
			System.out.println("Help Button Pressed");
			break;
		case ComponentPanel.SELECT:
			System.out.println("SELECT Button Pressed");
			doIt();
			break;		
		default:  
			System.out.println("What the heck happened");
			break;
		}
	}
	
	/**
	 * 
	 */
	private void doIt(){
		//TODO: SERVERLIST SHOULD NOT BE PROTECTED IN CLASS
        System.out.println("Did it");
		
        changeServerPanel.dispose();
	}
	
}
