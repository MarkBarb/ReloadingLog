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
import com.reloading.components.Bullet;
import com.reloading.components.Case;
import com.reloading.components.Firearm;
import com.reloading.components.Powder;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;

public class FirearmsTab extends ReloadingLogTab {

	public FirearmsTab(ReloadingLogBrowser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public FirearmsTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(browser, layout);
		// TODO Auto-generated constructor stub
	}
	public void buildDisplayTable(ArrayList<Firearm> firearms) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.FIREARM_COLUMNS, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		componentTable = new JTable(tableModel);
		// this.add(componentTable);
		for (int idx = 0; idx < firearms.size(); idx++) {
			Firearm firearm = firearms.get(idx);
			addFirearm(firearm);
		}
		componentTable.addMouseListener(new FirearmTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}
	/**
	 * Adds a bullet to the JTable
	 * @param bullet
	 */
	protected void addFirearm(Firearm firearm){
		Object[] row = { firearm
				,firearm.getManufacturer()
				,firearm.getModel()
				,firearm.getSerial()};
		tableModel.addRow(row);
		tab.validate();
	
	}
	
	@Override
	public void handleReloadingEvent(ReloadingEvent event) throws ReloadingException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 * 
	 */
	private class FirearmTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = componentTable.getSelectedRow();

				Firearm firearm = (Firearm) componentTable.getValueAt(row, 0);
				if (FirearmsDialog.openUpdateDialog(browser.getFrame(), 
						"Update Firearm:"
						, firearm)) {
					browser.saveFirearm(firearm);
					tableModel.setValueAt(firearm.toString(), row, 0);
					tableModel.setValueAt(firearm.getManufacturer(), row, 1);
					tableModel.setValueAt(firearm.getModel(), row, 2);
					tableModel.setValueAt(firearm.getSerial(), row, 3);
					tab.validate();
				}
				
			}
		}
	}
}
