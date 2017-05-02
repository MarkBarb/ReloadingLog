package com.reloading.browser;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Case;
import com.reloading.components.CastBullet;
import com.reloading.components.Primer;

public class PrimerDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Primer primer) {
		JTextField longName = new JTextField(primer.getName());
		JTextField shortName = new JTextField(primer.getShortName());
		JTextField manufacturer = new JTextField(primer.getManufacturer());
		
		Object [] fields= {
			    "Display Name:", shortName
			    ,"Name:", longName
			    ,"Manufacturer:", manufacturer
                };
		
		int option = JOptionPane.showConfirmDialog(frame
				,fields
				,action
				,JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
			primer.setShortName(shortName.getText());
			primer.setName(longName.getText());
			primer.setManufacturer(manufacturer.getText());
			return true;
		}
		return false;
	}
}
