package com.reloading.target;

import com.reloading.Constants;
import com.reloading.browser.ReloadingLogBrowser;
import com.reloading.testing.Shot;

//java imports
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;

public class TargetEvaluator extends JFrame {

	protected PropertyResourceBundle propertyResourceBundle;

	private static DecimalFormat df2 = new DecimalFormat(".##");
	private static DecimalFormat df0 = new DecimalFormat(".");

	JFrame myFrame;
	protected TargetEvaluator targetEvaluator;
	protected TargetEvaluatorAdaptor targetEvaluatorAdaptor;
	TargetEvaluatorPanel imagePanel;
	protected String fileName;
	// protected JLabel imageLabel = new JLabel();
	// protected JPanel imagePanel = new JPanel();
	private Image targetImage;
	private TargetEvaluatorTest test;

	private int clickCount = 0;

	public static final int PADDING = 5;

	public static final int IMAGE_WIDTH = 500;
	public static final int IMAGE_HEIGHT = 600;

	public static final int LIST_WIDTH = 30;
	public static final int DISPLAY_WIDTH = 150;

	public static final int CONTROL_HEIGHT = 30;
	public static final int BUTTONS_HEIGHT = 100;

	public static final int START_SIZE = 700;
	
	
	//KEYS FOR PROPERTY FILE FOR DEFAULT VALUES
	//
	public static final String WITNESS_UPPER_LEFT_X_KEY = "WITNESS_UPPER_LEFT_X";
	public static final String WITNESS_UPPER_LEFT_Y_KEY = "WITNESS_UPPER_LEFT_Y";
	public static final String WITNESS_UPPER_RIGHT_X_KEY = "WITNESS_UPPER_RIGHT_X";
	public static final String WITNESS_UPPER_RIGHT_Y_KEY = "WITNESS_UPPER_RIGHT_Y";
	public static final String WITNESS_LOWER_LEFT_X_KEY = "WITNESS_LOWER_LEFT_X";
	public static final String WITNESS_LOWER_LEFT_Y_KEY = "WITNESS_LOWER_LEFT_Y";
	public static final String WITNESS_WIDTH_KEY = "WITNESS_WIDTH";
	public static final String WITNESS_HEIGHT_KEY = "WITNESS_HEIGHT";
	public static final String AIM_POINT_X_KEY = "AIM_POINT_X";
	public static final String AIM_POINT_Y_KEY = "AIM_POINT_Y";
	
	private int witnessUpperLeftX = 20;
	private int witnessUpperLeftY = 435;

	private int witnessUpperRightX = 515;
	private int witnessUpperRightY = 435;

	private int witnessLowerLeftX = 20;
	private int witnessLowerLeftY = 565;

	private int aimPointX = 275;
	private int aimPointY = 300;

	private double witnessX = 7.5;
	private double witnessY = 3.375;

	//

	// Display results of test stuff,Left hand side
	protected JLabel groupSize;

	protected JLabel groupCenter;

	protected JLabel stdGroup;

	protected JLabel aveVelocity;
	protected JLabel stdVelocity;

	protected JScrollPane shotScrollPane;
	protected JTable shotTable;
	protected DefaultTableModel shotTableModel;

	// Button Panel stuff at the bottom.
	// buttons
	public static final String UPPER_RIGHT_WITNESS = "Upper Right";
	public static final String UPPER_LEFT_WITNESS = "Upper Left";
	public static final String LOWER_LEFT_WITNESS = "Lower Left";
	public static final String WITNESS_HEIGHT = "Height";
	public static final String WITNESS_WIDTH = "Width";
	public static final String AIMING_POINT = "Aim Point";
	public static final String SHOT = "Shot";

	protected ButtonGroup buttonGroup;
	protected JRadioButton witnessUpperLeftButton;
	protected JLabel witnessUpperLeftLabel;
	protected JRadioButton witnessUpperRightButton;
	protected JLabel witnessUpperRightLabel;
	protected JRadioButton witnessLowerLeftButton;
	protected JLabel witnessLowerLeftLabel;
	protected JRadioButton aimingPointButton;
	protected JLabel aimingPointLabel;
	protected JRadioButton shotButton;
	protected JLabel shotLabel;

	protected JFormattedTextField witnessHeight;
	protected JLabel witnessHeightLabel;
	protected JFormattedTextField witnessWidth;
	protected JLabel witnessWidthLabel;
	private NumberFormat targetNumberFormat;

	/*************************************************************/
	/* Constructors */
	/*************************************************************/

	private TargetEvaluator() {
		super();
		this.setLayout(new BorderLayout());
		test = new TargetEvaluatorTest();
		myFrame = this;
	}

	public TargetEvaluator(String scannedTarget) {
		super();
		this.setLayout(new BorderLayout());
		test = new TargetEvaluatorTest();
		myFrame = this;

		Point2D.Double witnessUpperLeft = new Point2D.Double(witnessUpperLeftX, witnessUpperLeftY);
		test.setWitnessUpperLeft(witnessUpperLeft);

		Point2D.Double witnessUpperRight = new Point2D.Double(witnessUpperRightX, witnessUpperRightY);
		test.setWitnessUpperRight(witnessUpperRight);

		Point2D.Double witnessLowerLeft = new Point2D.Double(witnessLowerLeftX, witnessLowerLeftY);
		test.setWitnessLowerLeft(witnessLowerLeft);

		Point2D.Double sightPoint = new Point2D.Double(aimPointX, aimPointY);
		test.setSightPoint(sightPoint);

		buildGui(scannedTarget);
		// Set thelayout
		//

	}

	public TargetEvaluator(ReloadingLogBrowser manual, String scannedTarget) {
		super();
		this.setLayout(new BorderLayout());
		test = new TargetEvaluatorTest();
		myFrame = this;

		Point2D.Double witnessUpperLeft = new Point2D.Double(witnessUpperLeftX, witnessUpperLeftY);
		test.setWitnessUpperLeft(witnessUpperLeft);

		Point2D.Double witnessUpperRight = new Point2D.Double(witnessUpperRightX, witnessUpperRightY);
		test.setWitnessUpperRight(witnessUpperRight);

		Point2D.Double witnessLowerLeft = new Point2D.Double(witnessLowerLeftX, witnessLowerLeftY);
		test.setWitnessLowerLeft(witnessLowerLeft);

		Point2D.Double sightPoint = new Point2D.Double(aimPointX, aimPointY);
		test.setSightPoint(sightPoint);

		System.out.println("Into TargetEvaluator Constructor: " + scannedTarget);
		buildGui(scannedTarget);
		// Set thelayout
		//

	}

	public TargetEvaluator(ReloadingLogBrowser manual, TargetEvaluatorTest test) {
		super();
		this.setLayout(new BorderLayout());
		this.test = test;

		myFrame = this;

		// set presentation values to test values
		Point2D testPoint = test.getWitnessUpperLeft();
		Double testDouble = new Double(testPoint.getX());
		witnessUpperLeftX = testDouble.intValue();
		testDouble = new Double(testPoint.getY());
		witnessUpperLeftY = testDouble.intValue();

		testPoint = test.getWitnessUpperRight();
		testDouble = new Double(testPoint.getX());
		witnessUpperRightX = testDouble.intValue();
		testDouble = new Double(testPoint.getY());
		witnessUpperRightY = testDouble.intValue();

		Point2D.Double witnessUpperRight = new Point2D.Double(witnessUpperRightX, witnessUpperRightY);
		test.setWitnessUpperRight(witnessUpperRight);

		Point2D.Double witnessLowerLeft = new Point2D.Double(witnessLowerLeftX, witnessLowerLeftY);
		test.setWitnessLowerLeft(witnessLowerLeft);

		Point2D.Double sightPoint = new Point2D.Double(aimPointX, aimPointY);
		test.setSightPoint(sightPoint);
		
		String scannedTarget = "";
		if (test.getScannedTarget().length() > 0) {
			scannedTarget = test.getScannedTarget();
		} else {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				scannedTarget = file.getAbsolutePath();
			}
		}
		// System.out.println("Into TargetEvaluator Constructor: " +
		// test.getScannedTarget());
		buildGui(scannedTarget);
		// Set thelayout
		//

	}

	/*************************************************************/
	/* Build GUI */
	/*************************************************************/
	private void buildGui(String scannedTarget) {
		// TODO Auto-generated constructor stub
		// targetEvaluator = new TargetEvaluator();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit Target Evaluator?",
						"Exit Evaluator", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					myFrame.dispose();
				}
			}
		});

		targetEvaluatorAdaptor = new TargetEvaluatorAdaptor();
		targetEvaluatorAdaptor.setTargetEvaluator(this);
		// targetEvaluatorAdaptor.setManual(manual);
		// targetEvaluator.setFileName(scannedTarget);
		setTitle("Target Evaluator");
		setVisible(true);
		setSize(LIST_WIDTH + PADDING + IMAGE_WIDTH + PADDING + DISPLAY_WIDTH,
				CONTROL_HEIGHT + PADDING + IMAGE_HEIGHT + PADDING + BUTTONS_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// System.out.println("Set stuff");
		// Image Panel
		targetImage = new ImageIcon(scannedTarget).getImage();
		imagePanel = new TargetEvaluatorPanel(targetEvaluatorAdaptor, targetImage);
		this.add(imagePanel, BorderLayout.CENTER);

		// controls at top panel
		JPanel controlPanel = buildControlPanel();
		this.add(controlPanel, BorderLayout.NORTH);

		// controls at BOTTOM of panel
		JPanel buttonsPanel = buildButtonPanel();
		this.add(buttonsPanel, BorderLayout.SOUTH);

		///// LIST ON LEFT SIDE
		// JPanel listPanel = buildListPanel();
		// this.add(listPanel,BorderLayout.WEST);

		// LIST ON LEFT SIDE
		JPanel displayPanel = buildDisplayPanel();
		this.add(displayPanel, BorderLayout.WEST);

		validate();

	}

	private JPanel buildButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
		GridLayout buttonLayout = new GridLayout(0, 6);

		Dimension size = new Dimension(LIST_WIDTH + IMAGE_WIDTH + DISPLAY_WIDTH, BUTTONS_HEIGHT);

		// targetImage.setSize();
		buttonPanel.setPreferredSize(size);
		buttonPanel.setMinimumSize(size);
		buttonPanel.setMaximumSize(size);
		buttonPanel.setSize(size);
		buttonPanel.setLayout(buttonLayout);

		//
		buttonGroup = new ButtonGroup();
		// FIRST ROW
		witnessUpperLeftButton = new JRadioButton(UPPER_LEFT_WITNESS);
		buttonGroup.add(witnessUpperLeftButton);
		buttonPanel.add(witnessUpperLeftButton);

		witnessUpperLeftLabel = new JLabel(
				Integer.toString(witnessUpperLeftX) + "," + Integer.toString(witnessUpperLeftY), SwingConstants.LEFT);
		buttonPanel.add(witnessUpperLeftLabel);

		witnessWidth = new JFormattedTextField(df2);
		witnessWidth.setValue(witnessX);
		PropertyChangeListener widthListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("propertyChangeListner");
				Double width = -1.0;
				try {
					width = Double.parseDouble(String.valueOf(evt.getNewValue()));
				} catch (NumberFormatException numberEx) {
					// JOptionPane.showMessageDialog(null, "Error: Number Format
					// Error", "Error Massage",
					// JOptionPane.ERROR_MESSAGE);
					witnessWidth.setValue(witnessX);
					return;
				}
				String text = evt.getNewValue() != null ? evt.getNewValue().toString() : "";
				witnessWidth.setValue(evt.getNewValue());
				witnessX = width;
				test.setWitnessX(width);
				test.recalculateAllPoints();
				refreshData();

			}

		};
		witnessWidth.addPropertyChangeListener(widthListener);
		buttonPanel.add(witnessWidth);

		witnessWidthLabel = new JLabel(WITNESS_WIDTH, SwingConstants.LEFT);
		buttonPanel.add(witnessWidthLabel);

		witnessUpperRightButton = new JRadioButton(UPPER_RIGHT_WITNESS);
		buttonGroup.add(witnessUpperRightButton);
		buttonPanel.add(witnessUpperRightButton);

		witnessUpperRightLabel = new JLabel(
				Integer.toString(witnessUpperRightX) + "," + Integer.toString(witnessUpperRightY), SwingConstants.LEFT);
		buttonPanel.add(witnessUpperRightLabel);

		// SECOND ROW
		witnessHeight = new JFormattedTextField(df2);
		witnessHeight.setValue(witnessY);
		PropertyChangeListener heightListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("propertyChangeListner");
				Double height = -1.0;
				try {
					height = Double.parseDouble(String.valueOf(evt.getNewValue()));
				} catch (NumberFormatException numberEx) {
					// JOptionPane.showMessageDialog(null, "Error: Number Format
					// Error", "Error Massage",
					// JOptionPane.ERROR_MESSAGE);
					witnessHeight.setValue(witnessY);
					return;
				}
				String text = evt.getNewValue() != null ? evt.getNewValue().toString() : "";
				witnessHeight.setValue(evt.getNewValue());
				witnessY = height;
				test.setWitnessY(height);
				test.recalculateAllPoints();
				refreshData();

			}

		};
		witnessHeight.addPropertyChangeListener(heightListener);
		buttonPanel.add(witnessHeight);

		witnessHeightLabel = new JLabel(WITNESS_HEIGHT, SwingConstants.LEFT);
		buttonPanel.add(witnessHeightLabel);

		aimingPointButton = new JRadioButton(AIMING_POINT);
		buttonGroup.add(aimingPointButton);
		buttonPanel.add(aimingPointButton);

		aimingPointLabel = new JLabel(Integer.toString(aimPointX) + "," + Integer.toString(aimPointY),
				SwingConstants.CENTER);
		buttonPanel.add(aimingPointLabel);

		shotButton = new JRadioButton(SHOT);
		buttonGroup.add(shotButton);
		shotButton.setSelected(true);
		buttonPanel.add(shotButton);

		shotLabel = new JLabel("x,y", SwingConstants.CENTER);
		buttonPanel.add(shotLabel);

		// Third ROW

		witnessLowerLeftButton = new JRadioButton(LOWER_LEFT_WITNESS);
		buttonGroup.add(witnessLowerLeftButton);
		buttonPanel.add(witnessLowerLeftButton);

		witnessLowerLeftLabel = new JLabel(
				Integer.toString(witnessLowerLeftX) + "," + Integer.toString(witnessLowerLeftY), SwingConstants.CENTER);
		buttonPanel.add(witnessLowerLeftLabel);

		return buttonPanel;
	}

	private JPanel buildControlPanel() {
		JPanel controlPanel = new JPanel();
		Dimension size = new Dimension(LIST_WIDTH + IMAGE_WIDTH + DISPLAY_WIDTH, CONTROL_HEIGHT);

		// targetImage.setSize();
		controlPanel.setPreferredSize(size);
		controlPanel.setMinimumSize(size);
		controlPanel.setMaximumSize(size);
		controlPanel.setSize(size);
		return controlPanel;
	}

	private JPanel buildDisplayPanel() {
		JPanel displayPanel = new JPanel();
		displayPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
		displayPanel.setLayout(new BorderLayout());
		Dimension size = new Dimension(DISPLAY_WIDTH, IMAGE_HEIGHT);

		// targetImage.setSize();
		displayPanel.setPreferredSize(size);
		displayPanel.setMinimumSize(size);
		displayPanel.setMaximumSize(size);
		displayPanel.setSize(size);

		// Display for the calculations
		JPanel calcPanel = new JPanel();
		calcPanel.setLayout(new GridLayout(0, 1));
		displayPanel.add(calcPanel, BorderLayout.NORTH);

		// Group Size
		groupSize = new JLabel("Group Size:");
		calcPanel.add(groupSize);
		// Group Center
		groupCenter = new JLabel("Group Center:");
		calcPanel.add(groupCenter);
		// Windage
		stdGroup = new JLabel("Std Group:");
		calcPanel.add(stdGroup);
		// Ave Velocity
		aveVelocity = new JLabel("Ave Velocity: ");
		calcPanel.add(aveVelocity);
		// std Velocity
		stdVelocity = new JLabel("Std Velocity: ");
		calcPanel.add(stdVelocity);

		displayPanel.add(calcPanel, BorderLayout.NORTH);

		// Display Shots
		String[] headers = { "Shot", "Wind", "Ele", "Vel" };
		shotTableModel = new DefaultTableModel(headers, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 3) return true;
				return false;
			}
			
		};

		shotTable = new JTable(shotTableModel);
		shotScrollPane = new JScrollPane(shotTable);

		displayPanel.add(shotScrollPane, BorderLayout.CENTER);

		return displayPanel;
	}

	private JPanel buildListPanel() {
		JPanel listPanel = new JPanel();
		Dimension size = new Dimension(LIST_WIDTH, IMAGE_HEIGHT);

		// targetImage.setSize();
		listPanel.setPreferredSize(size);
		listPanel.setMinimumSize(size);
		listPanel.setMaximumSize(size);
		listPanel.setSize(size);
		return listPanel;
	}

	/*************************************************************/
	/**/
	/*************************************************************/

	public void handleMouseClicked(MouseEvent e) {
		// System.out.println("Mouse Click x,y " + e.getX() + "," + e.getY());
		if (witnessUpperRightButton.isSelected()) {
			test.setWitnessUpperRight(e.getPoint());
			test.recalculateAllPoints();
			witnessUpperRightLabel.setText(e.getX() + "," + e.getY());
			witnessUpperRightButton.setSelected(false);
		} else if (witnessUpperLeftButton.isSelected()) {
			test.setWitnessUpperLeft(e.getPoint());
			test.recalculateAllPoints();
			witnessUpperLeftLabel.setText(e.getX() + "," + e.getY());
			witnessUpperLeftButton.setSelected(false);
		} else if (witnessLowerLeftButton.isSelected()) {
			test.setWitnessLowerLeft(e.getPoint());
			test.recalculateAllPoints();
			witnessLowerLeftLabel.setText(e.getX() + "," + e.getY());
			witnessLowerLeftButton.setSelected(false);
		} else if (aimingPointButton.isSelected()) {
			test.setSightPoint(e.getPoint());
			test.recalculateAllPoints();
			aimingPointLabel.setText(e.getX() + "," + e.getY());
			aimingPointButton.setSelected(false);
		} else if (shotButton.isSelected()) {
			// TODO: NEED TO SEE IF WE ARE EDITTING A SHOT
			clickCount++;
			test.addShotPoint(e.getPoint());
			shotLabel.setText(e.getX() + "," + e.getY());
		}

		refreshData();
	}

	private void refreshData() {

		if (clickCount > 2) {
			groupSize.setText("GroupSize: " + df2.format(test.getGroupSize()));
			Point2D center = test.getGroupCenter();
			groupCenter.setText("GroupCenter: " + df2.format(center.getX()) + "," + df2.format(center.getY()));
			stdGroup.setText("windSD: " + df2.format(test.getWindageSD()) + "  eleSD: " + df2.format(test.getElevationSD()));
			if (test.getAverageVelocity() > 0) {
				aveVelocity.setText("Ave Velocity: " + df0.format(test.getAverageVelocity()));
				stdVelocity.setText("Std Velocity: " + df2.format(test.getVelocitySD()));
			}

		}
		shotTableModel.setRowCount(0);
		ArrayList<Shot> shots = test.getShotsList();
		for (int index = 0; index < shots.size(); index++) {
			TargetEvaluatorShot shot = (TargetEvaluatorShot) shots.get(index);

			String x = df2.format(shot.getWindage());
			String y = df2.format(shot.getElevation());
			String v = "";
			if (shot.getVelocity() > 0) {
				v = df0.format(shot.getVelocity());
			}
			Object[] row = { shot, x, y, v };
			shotTableModel.addRow(row);
		}
		this.validate();
	}

	public void setTargetEvaluator(TargetEvaluator targetEvaluator) {
		this.targetEvaluator = targetEvaluator;
	}

	public void setTargetEvaluatorAdaptor(TargetEvaluatorAdaptor targetEvaluatorAdaptor) {
		this.targetEvaluatorAdaptor = targetEvaluatorAdaptor;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setPropertyResourceBundle(PropertyResourceBundle propertyResourceBundle) {
		System.out.println("Setting Property Bundle");
		this.propertyResourceBundle = propertyResourceBundle;
		
		witnessUpperLeftX = Integer.parseInt(propertyResourceBundle.getString(WITNESS_UPPER_LEFT_X_KEY));
		witnessUpperLeftY = Integer.parseInt(propertyResourceBundle.getString(WITNESS_UPPER_LEFT_Y_KEY));
		Point2D.Double witnessUpperLeft = new Point2D.Double(witnessUpperLeftX, witnessUpperLeftY);
		test.setWitnessUpperLeft(witnessUpperLeft);System.out.println("Setting UL: " + Integer.toString(witnessUpperLeftX) + "," + Integer.toString(witnessUpperLeftY));
		witnessUpperLeftLabel.setText(Integer.toString(witnessUpperLeftX) + "," + Integer.toString(witnessUpperLeftY));
		
		witnessUpperRightX = Integer.parseInt(propertyResourceBundle.getString(WITNESS_UPPER_RIGHT_X_KEY));
		witnessUpperRightY = Integer.parseInt(propertyResourceBundle.getString(WITNESS_UPPER_RIGHT_Y_KEY));
		Point2D.Double witnessUpperRight = new Point2D.Double(witnessUpperRightX, witnessUpperRightY);
		test.setWitnessUpperRight(witnessUpperRight);
		witnessUpperRightLabel.setText(Integer.toString(witnessUpperRightX) + "," + Integer.toString(witnessUpperRightY));
		
		witnessLowerLeftX = Integer.parseInt(propertyResourceBundle.getString(WITNESS_LOWER_LEFT_X_KEY));
		witnessLowerLeftY = Integer.parseInt(propertyResourceBundle.getString(WITNESS_LOWER_LEFT_Y_KEY));
		Point2D.Double witnessLowerLeft = new Point2D.Double(witnessLowerLeftX, witnessLowerLeftY);
		
		test.setWitnessLowerLeft(witnessLowerLeft);
		witnessLowerLeftLabel.setText(Integer.toString(witnessLowerLeftX) + "," + Integer.toString(witnessLowerLeftY));
		
		
		aimPointX = Integer.parseInt(propertyResourceBundle.getString(AIM_POINT_X_KEY));
		aimPointY = Integer.parseInt(propertyResourceBundle.getString(AIM_POINT_Y_KEY));
		Point2D.Double sightPoint = new Point2D.Double(aimPointX, aimPointY);
		test.setSightPoint(sightPoint);
		aimingPointLabel.setText(Integer.toString(aimPointX) + "," + Integer.toString(aimPointY));
		
		
		witnessX = Double.parseDouble(propertyResourceBundle.getString(WITNESS_WIDTH_KEY));
		test.setWitnessX(witnessX);
		witnessWidth.setValue(witnessX);
		
		witnessY = Double.parseDouble(propertyResourceBundle.getString(WITNESS_HEIGHT_KEY));
		test.setWitnessY(witnessY);
		witnessHeight.setValue(witnessY);
		
		
		
	}

	public void warn(String message) {
		JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// This is where a real application would open the file.
			// System.out.println("Opening: " + file.getAbsolutePath() + ".");
			TargetEvaluator tEvaluator = new TargetEvaluator(file.getAbsolutePath());
			if (args.length >= 1) {
				String propertyFileName = args[0];
				try (FileInputStream fis = new FileInputStream(propertyFileName)) {
					PropertyResourceBundle resourceBundle = new PropertyResourceBundle(fis);
					tEvaluator.setPropertyResourceBundle(resourceBundle);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			tEvaluator.setVisible(true);
		} else {
			System.out.println("Open command cancelled by user.");
		}

	}

}
