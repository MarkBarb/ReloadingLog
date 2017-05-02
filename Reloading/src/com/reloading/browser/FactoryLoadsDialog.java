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

public class FactoryLoadsDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Load load, Factory factory) {
		int loadOption = 0;

		String className = load.getClass().getName();
		System.out.println(className);

		ArrayList<Cartridge> cartridgeList = factory.getCartridges();
		Object cartridges[] = cartridgeList.toArray();
		JComboBox<Cartridge> cartridgeChooser = new JComboBox(cartridges);
		if (load.getCartridgeId() > 0) {
			int selectedCartridgeId = load.getCartridgeId();
			for (int idx = 0; idx < cartridgeList.size(); idx++) {
				if (cartridgeList.get(idx).getId() == selectedCartridgeId) {
					cartridgeChooser.setSelectedItem(cartridgeList.get(idx));
					break;
				}
			}
		}

		JTextField longName = new JTextField(load.getName());
		JTextField shortName = new JTextField(load.getShortName());
		JTextField manufacturer = new JTextField(load.getManufacturer());

		JTextField comments = new JTextField(load.getComments());

		Object[] fields = { 
				"Cartridge: ", cartridgeChooser
				,"Manufacturer:", manufacturer
				,"Display Name:", shortName
				,"Name:", longName
				,"Comments:", comments };

		loadOption = JOptionPane.showConfirmDialog(frame, fields, action, JOptionPane.OK_CANCEL_OPTION);

		if (loadOption == JOptionPane.OK_OPTION) {

			Cartridge cartridge = (Cartridge) cartridgeChooser.getSelectedItem();
			load.setCartridge(cartridge);
			load.setShortName(shortName.getText());
			load.setName(longName.getText());
			load.setManufacturer(manufacturer.getText());
			load.setComments(comments.getText());

			return true;
		}

		return false;
	}
}
