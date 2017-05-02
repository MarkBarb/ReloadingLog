package com.reloading.browser;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.reloading.Constants;
import com.reloading.components.Cartridge;
import com.reloading.components.Component;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;

public class CartridgesTab extends ReloadingLogTab {
	private JButton saveButton;


	public CartridgesTab(ReloadingLogBrowser browser) {
		super(browser);
		this.browser = browser;

	}

	public CartridgesTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(browser,layout);
		this.browser = browser;
	}

	public void buildDisplayTable(ArrayList<Cartridge> cartridges) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.CARTRIDGE_COLUMNS, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		componentTable = new JTable(tableModel);
		// this.add(componentTable);
		for (int idx = 0; idx < cartridges.size(); idx++) {
			Cartridge cartridge = cartridges.get(idx);
			addCartridge(cartridge);
		}
		componentTable.addMouseListener(new CartridgeTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}

	protected void addCartridge(Cartridge cartridge) {
		Object[] row = { cartridge, cartridge.getName() };
		tableModel.addRow(row);
		tab.validate();
	}

	@Override
	public void handleReloadingEvent(ReloadingEvent event) throws ReloadingException {
		// TODO Auto-generated method stub

	}
/**
 * Internal class to handle mouse clicks
 * @author mbarb
 *
 */
	private class CartridgeTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = componentTable.getSelectedRow();

				Cartridge cartridge = (Cartridge) componentTable.getValueAt(row, 0);
				//System.out.println("CartridgeID: " + cartridge.getId());
				
				if (CartridgeDialog.openUpdateDialog(browser.getFrame(), 
						"Update Cartridge:"
						, cartridge)) {
					
					browser.saveCartridge(cartridge);

					tableModel.setValueAt(cartridge.getName(), row, 1);
					tab.validate();
				}
			}
		}
	}
}
