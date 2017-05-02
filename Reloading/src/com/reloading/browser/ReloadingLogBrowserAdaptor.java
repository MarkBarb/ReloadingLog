package com.reloading.browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.reloading.components.*;

public class ReloadingLogBrowserAdaptor extends MouseAdapter implements ActionListener {
	protected ReloadingLogBrowser browser;
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		//System.out.println("actionCommand: " + actionCommand);
		switch (actionCommand) {
		case ReloadingLogBrowser.ADD_COMPONENT:
			//System.out.println(ReloadingLogBrowser.ADD_COMPONENT + " Button Pressed");
			break;
		case ReloadingLogBrowser.EVALUATE_TARGET:
			//System.out.println(ReloadingLogBrowser.EVALUATE_TARGET + " Button Pressed");
			browser.evaluateTarget();
			break;
		case ReloadingLogBrowser.OPEN:
			//System.out.println(ReloadingLogBrowser.OPEN + " menu Pressed");
			break;
		case ReloadingLogBrowser.ADD_CARTRIDGE:
			//System.out.println(ReloadingLogBrowser.ADD_CARTRIDGE + " menu Pressed");
			Cartridge cartridge = new Cartridge();
			if (CartridgeDialog.openUpdateDialog(browser.getFrame(), 
					"Add Cartridge:"
					, cartridge)) {
				
				browser.saveCartridge(cartridge);;
			}
			
			break;
		case ReloadingLogBrowser.ADD_CASE:
			//System.out.println(ReloadingLogBrowser.ADD_CASE + " menu Pressed");
			Case nCase = new Case();
			
			if (CaseDialog.openUpdateDialog(browser.getFrame(), "Add Case", nCase,browser.getFactory())) {
				browser.saveCase(nCase);
			}
			break;
			
		case ReloadingLogBrowser.ADD_BULLET:
			Bullet bullet = new Bullet();
			if (BulletDialog.openUpdateDialog(browser.getFrame(), 
					"Add Bullet:"
					, bullet)) {
				
				browser.saveBullet(bullet);
			}
			break;
		case ReloadingLogBrowser.ADD_CASTBULLET:
			//System.out.println(ReloadingLogBrowser.ADD_BULLET + " menu Pressed");

			CastBullet castBullet = new CastBullet();
			if (BulletDialog.openUpdateDialog(browser.getFrame(), 
					"Add Cast Bullet:"
					, castBullet)) {
				browser.saveBullet(castBullet);
			
			}
			break;
		
			case ReloadingLogBrowser.ADD_FIREARM:
			//System.out.println(ReloadingLogBrowser.ADD_LOAD + " menu Pressed");
			Firearm firearm = new Firearm();

			if (FirearmsDialog.openUpdateDialog(browser.getFrame()
					,ReloadingLogBrowser.ADD_FIREARM 
					, firearm)) {
				browser.saveFirearm(firearm);
			
			}
			break;
		case ReloadingLogBrowser.ADD_LOAD:
			//System.out.println(ReloadingLogBrowser.ADD_LOAD + " menu Pressed");
			Reload load = new Reload();

			if (ReloadsDialog.openUpdateDialog(browser.getFrame(), 
					"Add Load:"
					, load
					,browser.getFactory())) {
				browser.saveLoad(load);
			
			}
			break;
			
		case ReloadingLogBrowser.ADD_FACTORYLOAD:
			//System.out.println(ReloadingLogBrowser.ADD_LOAD + " menu Pressed");
			FactoryLoad factoryLoad = new FactoryLoad();

			if (FactoryLoadsDialog.openUpdateDialog(browser.getFrame(), 
					"Add Factory Load:"
					, factoryLoad
					,browser.getFactory())) {
				browser.saveLoad(factoryLoad);
			
			}
			break;	
		case ReloadingLogBrowser.ADD_POWDER:
			//System.out.println(ReloadingLogBrowser.ADD_POWDER + " menu Pressed");

			Powder powder = new Powder();
			if (PowderDialog.openUpdateDialog(browser.getFrame(), 
					"Add Powder:"
					, powder)) {
				browser.savePowder(powder);
			
			}
			
			break;
		case ReloadingLogBrowser.ADD_PRIMER :
			//System.out.println(ReloadingLogBrowser.ADD_PRIMER  + " menu Pressed");
			
			Primer primer = new Primer();
			if (PrimerDialog.openUpdateDialog(browser.getFrame(), 
					"Add Primer:"
					, primer)) {
				browser.savePrimer(primer);
			
			}
			break;
		default:
			//System.out.println("What the heck happened");
			break;
		}
	}
	
	protected void setBrowser(ReloadingLogBrowser browser){
		this.browser = browser;
	}
}
