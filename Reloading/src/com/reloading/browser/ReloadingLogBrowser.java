package com.reloading.browser;
//imports for custom work

import com.reloading.Constants;
import com.reloading.components.*;
import com.reloading.exceptions.ReloadingException;
import com.reloading.factory.*;
import com.reloading.target.*;
import com.reloading.xml.XmlFactory;
import com.reloading.sql.SqlFactory;

//java imports
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReloadingLogBrowser {
	protected static ReloadingLogBrowser browser;
	protected static Factory factory;
	protected static JFrame frame;
	protected static JTabbedPane tabs;
	protected static BulletsTab bulletsTab;
	protected static CartridgesTab cartridgesTab;
	protected static CasesTab casesTab;
	protected static PowdersTab powdersTab;
	protected static PrimersTab primersTab;
	protected static ReloadsTab reloadsTab;
	protected static FactoryLoadsTab factoryLoadsTab;
	protected static FirearmsTab firearmsTab;

	protected static final String ADD_BULLET = "Add Bullet";
	protected static final String ADD_CASE = "Add Case";
	protected static final String ADD_CASTBULLET = "Add Cast Bullet";
	protected static final String ADD_CARTRIDGE = "Add Cartridge";
	protected static final String ADD_LOAD = "Add Reload";
	protected static final String ADD_FACTORYLOAD = "Add Factory Load";
	protected static final String ADD_FIREARM = "Add Firearm";
	protected static final String ADD_POWDER = "Add Powder";
	protected static final String ADD_PRIMER = "Add Primer";
	protected static final String ADD_COMPONENT = "Add Component";
	protected static final String FACTORY_CLASS_KEY = "FACTORYCLASS";
	protected static final String EVALUATE_TARGET = "Evaluate Target";
	protected static final String FILE = "File";
	protected static final String OPEN = "Open";

	protected static final String DBL_CLK_CARTRIDGE = "Double Click Cartridge";

	public static void main(String[] args) {

		browser = new ReloadingLogBrowser();

		String configFile = args[0];
		try (FileInputStream fis = new FileInputStream(configFile)) {
			PropertyResourceBundle resourceBundle = new PropertyResourceBundle(fis);
			String factoryClassName = resourceBundle.getString(FACTORY_CLASS_KEY);
			Class factoryClass = Class.forName(factoryClassName);
			factory = (Factory) factoryClass.newInstance();
			factory.setPropertyResourceBundle(resourceBundle);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				browser.createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {

		// Build the Adapter
		ReloadingLogBrowserAdaptor adapter = new ReloadingLogBrowserAdaptor();

		adapter.setBrowser(browser);
		// Create and set up the window.
		frame = new JFrame("ReloadingLogBrowser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		// frame.setSize(1200, 1200);
		Dimension size = new Dimension(Constants.LOG_WIDTH, Constants.LOG_HEIGHT);
		frame.setPreferredSize(size);
		// Build the menu
		JMenuBar menuBar = new JMenuBar();
		frame.add(menuBar, BorderLayout.NORTH);
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

		JMenu testMenu = new JMenu("Test");
		menuBar.add(testMenu);

		// Cartridge menuItem
		JMenuItem evalTargetItem = new JMenuItem(EVALUATE_TARGET);
		testMenu.add(evalTargetItem);
		evalTargetItem.addActionListener(adapter);
		evalTargetItem.setActionCommand(EVALUATE_TARGET);

		JMenu cartidgeMenu = new JMenu("Cartridge");
		menuBar.add(cartidgeMenu);

		JMenuItem addCartridgeItem = new JMenuItem("Add");
		cartidgeMenu.add(addCartridgeItem);
		addCartridgeItem.addActionListener(adapter);
		addCartridgeItem.setActionCommand(ADD_CARTRIDGE);

		// Components Menu
		JMenu componentMenu = new JMenu("Components");
		menuBar.add(componentMenu);

		JMenuItem addBulletItem = new JMenuItem(ADD_BULLET);
		addBulletItem.addActionListener(adapter);
		addBulletItem.setActionCommand(ADD_BULLET);
		componentMenu.add(addBulletItem);

		JMenuItem addCastBulletItem = new JMenuItem(ADD_CASTBULLET);
		addCastBulletItem.addActionListener(adapter);
		addCastBulletItem.setActionCommand(ADD_CASTBULLET);
		componentMenu.add(addCastBulletItem);

		//
		// case menu
		//
		// caseMenu = new JMenu("Cases");
		// menuBar.add(caseMenu);

		JMenuItem addCaseItem = new JMenuItem(ADD_CASE);
		componentMenu.add(addCaseItem);
		addCaseItem.addActionListener(adapter);
		addCaseItem.setActionCommand(ADD_CASE);

		//
		// Powder menu
		//
		// JMenu powderMenu = new JMenu("Powder");
		// menuBar.add(powderMenu);

		JMenuItem addPowderItem = new JMenuItem(ADD_POWDER);
		componentMenu.add(addPowderItem);
		addPowderItem.addActionListener(adapter);
		addPowderItem.setActionCommand(ADD_POWDER);

		// Primer menu
		//
		// JMenu primerMenu = new JMenu("Primer");
		// menuBar.add(primerMenu);

		JMenuItem addPrimerItem = new JMenuItem(ADD_PRIMER);
		componentMenu.add(addPrimerItem);
		addPrimerItem.addActionListener(adapter);
		addPrimerItem.setActionCommand(ADD_PRIMER);

		// Load menu
		//
		JMenu loadMenu = new JMenu("Load");
		menuBar.add(loadMenu);

		JMenuItem addLoadItem = new JMenuItem(ADD_LOAD);
		loadMenu.add(addLoadItem);
		addLoadItem.addActionListener(adapter);
		addLoadItem.setActionCommand(ADD_LOAD);

		JMenuItem addFactoryLoadItem = new JMenuItem(ADD_FACTORYLOAD);
		loadMenu.add(addFactoryLoadItem);
		addFactoryLoadItem.addActionListener(adapter);
		addFactoryLoadItem.setActionCommand(ADD_FACTORYLOAD);
		
		// Primer menu
		//
		JMenu firearmMenu = new JMenu("Firearms");
		menuBar.add(firearmMenu);


		JMenuItem addFirearmItem = new JMenuItem(ADD_FIREARM);
		firearmMenu.add(addFirearmItem);
		addFirearmItem.addActionListener(adapter);
		addFactoryLoadItem.setActionCommand(ADD_FIREARM);
		
		tabs = new JTabbedPane();
		frame.add(tabs, BorderLayout.CENTER);

		ArrayList<Cartridge> cartridges = factory.getCartridges();
		cartridgesTab = new CartridgesTab(browser);
		cartridgesTab.buildDisplayTable((ArrayList<Cartridge>) cartridges);
		tabs.addTab("Cartridges", cartridgesTab);

		ArrayList<Load> loads = factory.getLoads();
		reloadsTab = new ReloadsTab(browser);
		reloadsTab.buildDisplayTable(loads);
		tabs.addTab("Reloads", reloadsTab);

		factoryLoadsTab = new FactoryLoadsTab(browser);
		factoryLoadsTab.buildDisplayTable(loads);
		tabs.addTab("FactoryLoads", factoryLoadsTab);
		
		bulletsTab = new BulletsTab(browser);
		ArrayList<Bullet> bullets = factory.getBullets();
		bulletsTab.buildDisplayTable(bullets);
		tabs.addTab("Bullets", bulletsTab);
		// cases Tab
		casesTab = new CasesTab(browser);
		ArrayList<Case> cases = factory.getCases();
		casesTab.buildDisplayTable(cases);
		tabs.addTab("Cases", casesTab);

		
		powdersTab = new PowdersTab(browser);
		ArrayList<Powder> powders = factory.getPowders();
		powdersTab.buildDisplayTable(powders);
		tabs.addTab("Powder", powdersTab);

		primersTab = new PrimersTab(browser);
		ArrayList<Primer> primers = factory.getPrimers();
		primersTab.buildDisplayTable(primers);
		tabs.addTab("Primers", primersTab);
		
		firearmsTab = new FirearmsTab(browser);
		ArrayList<Firearm> firearms = factory.getFirearms();
		firearmsTab.buildDisplayTable(firearms);
		tabs.addTab("Firearms", firearmsTab);
		
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void evaluateTarget() {
		// System.out.println("Loading Imagefile:");
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// This is where a real application would open the file.
			// System.out.println("Opening: " + file.getAbsolutePath() + ".");
			TargetEvaluator tEvaluator = new TargetEvaluator(browser, file.getAbsolutePath());
			tEvaluator.setVisible(true);
		} else {
			System.out.println("Open command cancelled by user.");
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	public Factory getFactory() {
		return factory;
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void saveBullet(Bullet bullet) {
		try {
			int bulletID = bullet.getId();

			factory.saveBullet(bullet);
			if (bulletID < 0) {
				bulletsTab.addBullet(bullet);
			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Add Cartridge Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void saveCartridge(Cartridge cartridge) {
		try {
			int cartridgeID = cartridge.getId();

			factory.saveCartridge(cartridge);
			if (cartridgeID < 0) {
				cartridgesTab.addCartridge(cartridge);
			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Save Cartridge Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void saveCase(Case nCase) {
		try {
			int caseID = nCase.getId();

			factory.saveCase(nCase);
			if (caseID < 0) {
				casesTab.addCase(nCase);
			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Save Cartridge Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void saveFirearm(Firearm firearm) {
		try {
			int firearmID = firearm.getId();

			factory.saveFirearm(firearm);
			if (firearmID < 0) {
				firearmsTab.addFirearm(firearm);
			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Save Primer Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void saveLoad(Load load) {
		try {
			int loadID = load.getId();

			String className = load.getClass().getName();
			//System.out.println("SaveLoad: classname: " + className);

			factory.saveLoad(load);
			if (loadID < 0) {
				switch (className) {
				case Constants.FACTORYLOAD_CLASSNAME:
					factoryLoadsTab.addLoad((FactoryLoad) load);
					break;
				case Constants.RELOAD_CLASSNAME:
					reloadsTab.addLoad((Reload)load);
					break;
				default:
					System.out.println("Unknown Load Type");
				}

			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Save Load Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void savePowder(Powder powder) {
		try {
			int powderID = powder.getId();

			factory.savePowder(powder);
			if (powderID < 0) {
				powdersTab.addPowder(powder);
			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Save Powder Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/****************************************************************/
	/*                                                              */
	/****************************************************************/

	protected void savePrimer(Primer primer) {
		try {
			int primerID = primer.getId();

			factory.savePrimer(primer);
			if (primerID < 0) {
				primersTab.addPrimer(primer);
			}

		} catch (ReloadingException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Save Primer Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
