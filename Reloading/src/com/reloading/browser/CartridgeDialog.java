package com.reloading.browser;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.components.Case;
import com.reloading.components.CastBullet;

public class CartridgeDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Cartridge cartridge) {
		JTextField longCaseName = new JTextField(cartridge.getName());
		JTextField shortCaseName = new JTextField(cartridge.getShortName());
		JTextField nominalSize = new JTextField(cartridge.getNominalSizeString());
		
		Object [] caseFields= {
			    "Display Name:", shortCaseName
			    ,"Name:", longCaseName
			    ,"Nominal Size:", nominalSize
                };
		
		int cartridgeOption = JOptionPane.showConfirmDialog(frame
				,caseFields
				,action
				,JOptionPane.OK_CANCEL_OPTION);
		
		if (cartridgeOption == JOptionPane.OK_OPTION) {
			cartridge.setShortName(shortCaseName.getText());
			cartridge.setName(longCaseName.getText());
			cartridge.setNominalSize(nominalSize.getText());
			return true;
		}
		return false;
	}
}
