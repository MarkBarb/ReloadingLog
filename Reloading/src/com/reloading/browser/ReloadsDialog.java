package com.reloading.browser;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.components.CastBullet;
import com.reloading.components.Case;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.components.Reload;
import com.reloading.components.Load;
import com.reloading.factory.Factory;

public class ReloadsDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Load load, Factory factory) {
		int loadOption = 0;

		String className = load.getClass().getName();
		System.out.println(className);

		ArrayList<Cartridge> cartridgeList = factory.getCartridges();
		Object cartridges[] =  cartridgeList.toArray();
		JComboBox <Cartridge>cartridgeChooser = new JComboBox(cartridges);
		if (load.getCartridgeId() > 0){
			int selectedCartridgeId = load.getCartridgeId();
			for (int idx=0;idx<cartridgeList.size();idx++){
				if (cartridgeList.get(idx).getId() == selectedCartridgeId){
					cartridgeChooser.setSelectedItem(cartridgeList.get(idx));
					break;
				}
			}
		}
		
		switch (className) {
		case Constants.FACTORYLOAD_CLASSNAME:
			
			break;

		case Constants.RELOAD_CLASSNAME:
			Reload reload = (Reload) load;
			

			JTextField powderCharge = new JTextField(Double.toString((reload.getPowderCharge())));


			JTextField powderMeasureSetting = new JTextField(reload.getPowderMeasureSetting());
			
			ArrayList<Bullet> bulletList = factory.getBullets();
			Object bullets[] =  bulletList.toArray();
			JComboBox<Bullet> bulletChooser = new JComboBox(bullets);
			if (reload.getBulletId() > 0){
				int selectedBulletId = reload.getBulletId();
				for (int idx=0;idx<bulletList.size();idx++){
					if (bulletList.get(idx).getId() == selectedBulletId){
						bulletChooser.setSelectedItem(bulletList.get(idx));
						break;
					}
				}
			}
			bulletChooser.setSelectedItem(reload.getBullet());
			//System.out.println("selected bullet name: " + reload.getBullet().getShortName());
			ArrayList<Powder> powderList = factory.getPowders();
			Object powders[] =  powderList.toArray();
			
			JComboBox <Powder>powderChooser = new JComboBox(powders);
			if (reload.getPowderId() > 0){
				int selectedPowderId = reload.getPowderId();
				for (int idx=0;idx<powderList.size();idx++){
					if (powderList.get(idx).getId() == selectedPowderId){
						powderChooser.setSelectedItem(powderList.get(idx));
						break;
					}
				}
			}
			
			ArrayList<Primer> primerList = factory.getPrimers();
			Object primers[] =  primerList.toArray();
			JComboBox <Primer >primerChooser = new JComboBox(primers);
			if (reload.getPrimerId() > 0){
				int selectedPrimerId = reload.getPrimerId();
				for (int idx=0;idx<primerList.size();idx++){
					if (primerList.get(idx).getId() == selectedPrimerId){
						primerChooser.setSelectedItem(primerList.get(idx));
						break;
					}
				}
			}
			

			ArrayList<Case> caseList = factory.getCases();
			Object cases[] =  caseList.toArray();
			JComboBox caseChooser = new JComboBox(cases);
			int caseId = reload.getCaseId();
			if (caseId > 0){
				for (int idx=0;idx<caseList.size();idx++){
					if (caseList.get(idx).getId() == caseId){
						caseChooser.setSelectedItem(caseList.get(idx));
						break;
					}
				}
			}
			
			JTextField comments = new JTextField(load.getComments());
			JTextField overAllLength = new JTextField(Float.toString(reload.getOverAllLength()));
			
			Object [] loadFields= {
				    "Cartridge:", cartridgeChooser
				    ,"Bullet:", bulletChooser
				    ,"Powder Charge: ", powderCharge
				    ,"Powder:", powderChooser
				    ,"PowderMeasureSetting", powderMeasureSetting
				    ,"Primer:", primerChooser
				    ,"Case:", caseChooser
				    ,"Comments:", comments
				    ,"Overall Length:", overAllLength
	                };
			loadOption = JOptionPane.showConfirmDialog(frame, loadFields, action, JOptionPane.OK_CANCEL_OPTION);


			if (loadOption == JOptionPane.OK_OPTION) {

				Cartridge cartridge = (Cartridge) cartridgeChooser.getSelectedItem();
				reload.setCartridge(cartridge);
				
				Bullet bullet =(Bullet) bulletChooser.getSelectedItem();
				reload.setBullet(bullet);
				
				reload.setPowderCharge(powderCharge.getText());
				
				Powder powder = (Powder) powderChooser.getSelectedItem();
				reload.setPowder(powder);
				reload.setPowderMeasureSetting(powderMeasureSetting.getText());
				Primer primer = (Primer) primerChooser.getSelectedItem();
				reload.setPrimer(primer);
				
				Case casing = (Case) caseChooser.getSelectedItem();
				reload.setCase(casing);
				
				reload.setComments(comments.getText());
				reload.setOverAllLength(Float.parseFloat(overAllLength.getText()));
				
				return true;
			}
			break;
		}

		return false;
	}
}
