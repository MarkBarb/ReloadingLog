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
import com.reloading.components.Firearm;
import com.reloading.components.Case;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.components.Reload;
import com.reloading.components.Load;
import com.reloading.factory.Factory;

public class FirearmsDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Firearm firearm) {
		int loadOption = 0;
		String className = firearm.getClass().getName();

	

		JTextField shortName = new JTextField(firearm.getShortName());
		JTextField model = new JTextField(firearm.getModel());
		JTextField manufacturer = new JTextField(firearm.getManufacturer());
		JTextField serialNumber = new JTextField(firearm.getSerial());
		
		Object[] fields = { 
				"Firearm:", shortName
				,"Manufacturer:", manufacturer
				,"Model:", model
				,"Serial Number:", serialNumber };

		loadOption = JOptionPane.showConfirmDialog(frame, fields, action, JOptionPane.OK_CANCEL_OPTION);

		if (loadOption == JOptionPane.OK_OPTION) {
			firearm.setShortName(shortName.getText());
			firearm.setManufacturer(manufacturer.getText());
			firearm.setModel(model.getText());
			firearm.setSerial(serialNumber.getText());
			return true;
		}

		return false;
	}
}
