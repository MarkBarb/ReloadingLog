/**
 * 
 */
package com.reloading.target;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.*;

/**
 * @author Mark
 * 
 */
public class Scorer {

	protected static final String ADD_COMPONENT = "Add Component";
	protected static final String FILE = "file";

	protected static final String OPEN = "Open";
	private static  JFrame frame ;
	private static  Scorer scorer ;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scorer = new Scorer();
				scorer.createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Build the Adapter
		//scorer = new Scorer();
		ScorerAdapter adapter = new ScorerAdapter();
		adapter.setScorer(scorer);
		// Create and set up the window.
		System.out.println("Building gui");
		frame = new JFrame("Target Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		// Build the menu
		JMenuBar menuBar = new JMenuBar();
		frame.add(menuBar);
		JMenu fileMenu = new JMenu("File");

		menuBar.add(fileMenu);

		// Open menuItem
		JMenuItem openItem = new JMenuItem(OPEN);
		fileMenu.add(openItem);
		openItem.addActionListener(adapter);
		openItem.setActionCommand(OPEN);

		// Save menuItem
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);

		// close menuItem
		JMenuItem closeItem = new JMenuItem("Close");
		fileMenu.add(closeItem);
		// build a panel for the buttons
		/*
		 * JPanel jpButtonPanel = new JPanel(); jpButtonPanel.setLayout(new
		 * FlowLayout()); JButton addComponentButton = new
		 * JButton(ADD_COMPONENT);
		 * addComponentButton.addActionListener(adapter);
		 * 
		 * jpButtonPanel.add(addComponentButton);
		 * frame.add(jpButtonPanel,BorderLayout.NORTH);
		 */
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	protected void loadImageFile(){

		System.out.println("Loading Imagefile:");
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + "." );
        } else {
        	System.out.println("Open command cancelled by user." );
        }
	}
}
