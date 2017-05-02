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
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;

public class CasesTab extends ReloadingLogTab {

	public CasesTab(ReloadingLogBrowser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public CasesTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(browser, layout);
		// TODO Auto-generated constructor stub
	}
	public void buildDisplayTable(ArrayList<Case> cases) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.CASE_COLUMNS, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		componentTable = new JTable(tableModel);
		// this.add(componentTable);
		for (int idx = 0; idx < cases.size(); idx++) {
			Case nCase = cases.get(idx);
			addCase(nCase);
		}
		componentTable.addMouseListener(new CaseTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}
	/**
	 * Adds a bullet to the JTable
	 * @param bullet
	 */
	protected void addCase(Case nCase){
		Object[] row = { nCase
				,nCase.getName()
				,nCase.getManufacturer()};
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
	private class CaseTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = componentTable.getSelectedRow();

				Case nCase = (Case) componentTable.getValueAt(row, 0);

				if (CaseDialog.openUpdateDialog(browser.getFrame()
						, "Add Case"
						, nCase
						,browser.getFactory())) {
					browser.saveCase(nCase);
					tableModel.setValueAt(nCase.getName(), row, 1);
					tableModel.setValueAt(nCase.getManufacturer(), row, 2);
					tab.validate();
				}
			}
		}
	}
}
