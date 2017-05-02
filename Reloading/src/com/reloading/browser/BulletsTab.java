package com.reloading.browser;

import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.reloading.Constants;
//import com.reloading.browser.CartridgesTab.CartridgeTabMouseAdapter;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;

public class BulletsTab extends ReloadingLogTab {
	
	public BulletsTab(ReloadingLogBrowser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public BulletsTab(ReloadingLogBrowser browser,LayoutManager layout) {
		super(browser,layout);
		// TODO Auto-generated constructor stub
	}


	public void buildDisplayTable(ArrayList<Bullet> bullets) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.BULLET_COLUMNS, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		componentTable = new JTable(tableModel);
		// this.add(componentTable);
		for (int idx = 0; idx < bullets.size(); idx++) {
			Bullet bullet = bullets.get(idx);
			addBullet(bullet);
		}
		componentTable.addMouseListener(new BulletTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}
	
	@Override
	public void handleReloadingEvent(ReloadingEvent event) throws ReloadingException {
		// TODO Auto-generated method stub

	}
	/**
	 * Adds a bullet to the JTable
	 * @param bullet
	 */
	protected void addBullet(Bullet bullet){
		Object[] row = { bullet
				,bullet.getBallisticCoefficientString()
				,bullet.getDiameterString()
				,bullet.getStyle()
				,bullet.getWeightString()};
		tableModel.addRow(row);
		tab.validate();
	
	}

	private class BulletTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = componentTable.getSelectedRow();

				Bullet bullet = (Bullet) componentTable.getValueAt(row, 0);
				//System.out.println("ID: " + bullet.getId());
				if (BulletDialog.openUpdateDialog(browser.getFrame(), "Edit Bullet", bullet)){
					browser.saveBullet(bullet);
					tableModel.setValueAt(bullet.getBallisticCoefficientString(), row, 1);
					tableModel.setValueAt(bullet.getDiameterString(), row, 2);
					tableModel.setValueAt(bullet.getStyle(), row, 3);
					tableModel.setValueAt(bullet.getWeightString(), row, 4);
					
					tab.validate();
				}
			}
		}
	}
}
