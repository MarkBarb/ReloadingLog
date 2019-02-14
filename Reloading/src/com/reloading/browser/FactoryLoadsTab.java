package com.reloading.browser;

import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.components.Case;
import com.reloading.components.FactoryLoad;
import com.reloading.components.Load;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.components.Reload;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;
import com.reloading.factory.Factory;

public class FactoryLoadsTab extends ReloadingLogTab {

	TestPopupMenu menu; 
	
	public FactoryLoadsTab(ReloadingLogBrowser browser) {
		super(browser);
		menu = new TestPopupMenu(browser);
		// TODO Auto-generated constructor stub
	}

	public FactoryLoadsTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(browser, layout);
		menu = new TestPopupMenu(browser);
		// TODO Auto-generated constructor stub
	}

	public void buildDisplayTable(ArrayList<Load> loads) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.FACTORYLOAD_COLUMNS, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		componentTable = new JTable(tableModel);
		// this.add(componentTable);
		for (int idx = 0; idx < loads.size(); idx++) {
			Load load = loads.get(idx);
			String className = load.getClass().getName();
			//System.out.println(className);
			switch (className) {

			case Constants.FACTORYLOAD_CLASSNAME:
				addLoad((FactoryLoad) load);
				break;
			}
		}
		TableColumnModel tcm = componentTable.getColumnModel();
		//tcm.removeColumn(tcm.getColumn(0));

		componentTable.addMouseListener(new FactoryLoadTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}

	/**
	 * Adds a bullet to the JTable
	 * 
	 * @param bullet
	 */
	protected void addLoad(FactoryLoad load) {
		//System.out.println("ReloadsTab.addLoad");
		Cartridge cartridge = load.getCartridge();
		String cartridgeString = "Not Set";
		if (cartridge != null) {
			cartridgeString = cartridge.getShortName();
		}


		Object[] row = { load, cartridgeString, load.getManufacturer(), load.getShortName(),load.getComments() };
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
	private class FactoryLoadTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			int row = componentTable.getSelectedRow();
			FactoryLoad load = (FactoryLoad) componentTable.getValueAt(row, 0);
			if (e.getClickCount() == 2) {
				//System.out.println("ID: " + load.getId());

				if (FactoryLoadsDialog.openUpdateDialog(browser.getFrame(), "Update Load", load, browser.getFactory())) {
					browser.saveLoad(load);
					tableModel.setValueAt(load.getCartridge().getShortName(),row,1);
					tableModel.setValueAt(load.getManufacturer(),row,2);
					tableModel.setValueAt(load.getShortName(),row,3);
					tableModel.setValueAt(load.getComments(),row,4);
					tab.validate();
				}

			}
			else if (SwingUtilities.isRightMouseButton(e) ){
				menu.show(componentTable,e.getX() + 10,e.getY() - 20);
				menu.setLoad(load);
				menu.setVisible(true);
				}

		}
	}
}
