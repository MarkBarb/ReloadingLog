package com.reloading.browser;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.CastBullet;

public class BulletDialog {
	public static boolean openUpdateDialog(JFrame frame, String action, Bullet bullet) {
		int bulletOption = 0;

		JTextField longBulletName = new JTextField(bullet.getName());
		JTextField shortBulletName = new JTextField(bullet.getShortName());
		JTextField bulletManufacturer = new JTextField(bullet.getManufacturer());
		JTextField bulletDiameter = new JTextField(bullet.getDiameterString());
		JTextField ballisticCoefficient = new JTextField(bullet.getBallisticCoefficientString());
		JTextField bulletWeight = new JTextField(bullet.getWeightString());
		JTextField bulletStyle = new JTextField(bullet.getStyle());

		JTextField castBulletAlloy = new JTextField();
		JTextField asCastDiameter = new JTextField();
		JTextField lube = new JTextField();
		JCheckBox gasChecked = new JCheckBox();

		String className = bullet.getClass().getName();
		System.out.println(className);
		switch (className) {
		case Constants.BULLET_CLASSNAME:
			Object[] bulletFields = { "Display Name:", shortBulletName, "Manufacturer:", bulletManufacturer, "Name:",
					longBulletName, "Diameter:", bulletDiameter, "Ballistic Coefficient:", ballisticCoefficient,
					"Bullet Weight:", bulletWeight, "Bullet Style:", bulletStyle };

			bulletOption = JOptionPane.showConfirmDialog(frame, bulletFields, action, JOptionPane.OK_CANCEL_OPTION);
			break;

		case Constants.CASTBULLET_CLASSNAME:
			CastBullet castBullet = (CastBullet) bullet;

			castBulletAlloy.setText(castBullet.getAlloy());
			asCastDiameter.setText(Float.toString(castBullet.getAsCastDiameter()));
			lube.setText(castBullet.getLube());
			if (castBullet.isGasChecked()) {
				gasChecked.setSelected(true);
			}
			Object[] castBulletFields = { "Short Name:", shortBulletName, "Manufacturer:", bulletManufacturer, "Name:",
					longBulletName, "As Cast Diameter:", asCastDiameter, "Size To", bulletDiameter,
					"Ballistic Coefficient:", ballisticCoefficient, "Bullet Weight:", bulletWeight, "Bullet Style:",
					bulletStyle, "Gas Checked:", gasChecked, "Lube:", lube, "Alloy:", castBulletAlloy };

			bulletOption = JOptionPane.showConfirmDialog(frame, castBulletFields, action, JOptionPane.OK_CANCEL_OPTION);

			break;
		}

		if (bulletOption == JOptionPane.OK_OPTION) {
			
			bullet.setShortName(shortBulletName.getText());
			bullet.setName(longBulletName.getText());
			bullet.setManufacturer(bulletManufacturer.getText());
			bullet.setDiameter(Float.parseFloat(bulletDiameter.getText()));
			bullet.setBallisticCoefficient(Float.parseFloat(ballisticCoefficient.getText()));
			bullet.setWeight(bulletWeight.getText());
			bullet.setStyle(bulletStyle.getText());

			switch (className) {
			case Constants.BULLET_CLASSNAME:
				break;

			case Constants.CASTBULLET_CLASSNAME:
				CastBullet castBullet = (CastBullet) bullet;
				castBullet.setAlloy(castBulletAlloy.getText());
				castBullet.setAsCastDiameter(Float.parseFloat(asCastDiameter.getText()));
				castBullet.setLube(lube.getText());
				if (gasChecked.isSelected()) {
					castBullet.setGasChecked(true);
				}
			}
			return true;
		}
		return false;
	}
}
