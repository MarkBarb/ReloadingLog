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
import com.reloading.components.Powder;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;

public class PowdersTab extends ReloadingLogTab {

	public PowdersTab(ReloadingLogBrowser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public PowdersTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(browser, layout);
		// TODO Auto-generated constructor stub
	}
	public void buildDisplayTable(ArrayList<Powder> powders) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.POWDER_COLUMNS, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		componentTable = new JTable(tableModel);
		// this.add(componentTable);
		for (int idx = 0; idx < powders.size(); idx++) {
			Powder powder = powders.get(idx);
			addPowder(powder);
		}
		componentTable.addMouseListener(new PowderTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}
	/**
	 * Adds a bullet to the JTable
	 * @param bullet
	 */
	protected void addPowder(Powder powder){
		Object[] row = { powder
				,powder.getName()
				,powder.getManufacturer()};
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
	private class PowderTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = componentTable.getSelectedRow();

				Powder powder = (Powder) componentTable.getValueAt(row, 0);
				if (PowderDialog.openUpdateDialog(browser.getFrame(), 
						"Update Powder:"
						, powder)) {
					browser.savePowder(powder);
					tableModel.setValueAt(powder.getName(), row, 1);
					tableModel.setValueAt(powder.getManufacturer(), row, 2);
					tab.validate();
				}
				
			}
		}
	}
}
