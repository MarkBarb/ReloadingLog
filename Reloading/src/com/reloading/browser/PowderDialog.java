package com.reloading.browser;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Case;
import com.reloading.components.CastBullet;
import com.reloading.components.Powder;

public class PowderDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Powder powder) {
		JTextField longName = new JTextField(powder.getName());
		JTextField shortName = new JTextField(powder.getShortName());
		JTextField manufacturer = new JTextField(powder.getManufacturer());
		
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
			powder.setShortName(shortName.getText());
			powder.setName(longName.getText());
			powder.setManufacturer(manufacturer.getText());
			return true;
		}
		return false;
	}
}
