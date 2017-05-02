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
import com.reloading.components.Case;
import com.reloading.components.CastBullet;
import com.reloading.factory.Factory;

public class CaseDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Case nCase,Factory factory) {
		JTextField longCaseName = new JTextField(nCase.getName());
		JTextField shortCaseName = new JTextField(nCase.getShortName());
		JTextField caseManufacturer = new JTextField(nCase.getManufacturer());
		ArrayList<Cartridge> cartridgeList = factory.getCartridges();
		Object cartridges[] =  cartridgeList.toArray();
		JComboBox <Cartridge> cartridgeChooser = new JComboBox(cartridges);
		if (nCase.getCartridgeId() > 0){
			int selectedCartridgeId = nCase.getCartridgeId();
			for (int idx=0;idx<cartridgeList.size();idx++){
				if (cartridgeList.get(idx).getId() == selectedCartridgeId){
					cartridgeChooser.setSelectedItem(cartridgeList.get(idx));
					break;
				}
			}
		}
		
		
		Object [] caseFields= {
			    "Cartridge:", cartridgeChooser
			    ,"Display Name:", shortCaseName
			    ,"Name:", longCaseName
			    ,"Manufacturer:", caseManufacturer
                };
		
		int caseOption = JOptionPane.showConfirmDialog(frame
				,caseFields
				,action
				,JOptionPane.OK_CANCEL_OPTION);
		
		if (caseOption == JOptionPane.OK_OPTION) {
			nCase.setCartridge((Cartridge)cartridgeChooser.getSelectedItem());
			nCase.setShortName(shortCaseName.getText());
			nCase.setName(longCaseName.getText());
			nCase.setManufacturer(caseManufacturer.getText());
			return true;
		}
		return false;
	}
}
