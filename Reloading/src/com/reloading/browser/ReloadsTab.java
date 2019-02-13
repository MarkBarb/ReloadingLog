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
import com.reloading.components.Load;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.components.Reload;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;
import com.reloading.factory.Factory;

public class ReloadsTab extends ReloadingLogTab {
	TestPopupMenu menu; 
	public ReloadsTab(ReloadingLogBrowser browser) {
		super(browser);
		menu = new TestPopupMenu(browser);
		// TODO Auto-generated constructor stub
	}

	public ReloadsTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(browser, layout);
		// TODO Auto-generated constructor stub
	}

	public void buildDisplayTable(ArrayList<Load> loads) {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel(Constants.RELOAD_COLUMNS, 0) {

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
			System.out.println(className);
			switch (className) {

			case Constants.RELOAD_CLASSNAME:
				addLoad((Reload) load);
				break;
			}
		}
		TableColumnModel tcm = componentTable.getColumnModel();
		//tcm.removeColumn(tcm.getColumn(0));

		componentTable.addMouseListener(new ReloadTabMouseAdapter());
		scrollPane = new JScrollPane(componentTable);
		this.add(scrollPane);

	}

	/**
	 * Adds a bullet to the JTable
	 * 
	 * @param bullet
	 */
	protected void addLoad(Reload load) {
		System.out.println("ReloadsTab.addLoad");
		Cartridge cartridge = load.getCartridge();
		String cartridgeString = "Not Set";
		if (cartridge != null) {
			cartridgeString = cartridge.getShortName();
		}

		System.out.println(cartridgeString);
		
		Bullet bullet = load.getBullet();
		String bulletString = "Not Set" ;
		if (bullet != null &&  bullet.getShortName() != null) {
			bulletString = bullet.getShortName();
		} else {
			System.out.println("Why is bullet null");
		}
		Powder powder = load.getPowder();
		String powderString = "Not Set";
		if (powder != null) {
			powderString = load.getPowderCharge() + "gr " + powder.getShortName();
		}
		String powderMeasureSetting = load.getPowderMeasureSetting();
		
		Primer primer = load.getPrimer();
		String primerString = "Not Set";
		if (primer != null) {
			primerString = primer.getShortName();
		}
		Case nCase = load.getCasing();
		String caseString = "";
		if (nCase != null) {
			caseString = nCase.toString();
		}
		String overAllLenthString = Float.toString(load.getOverAllLength());
		String comment = load.getComments();
		if (comment.length() > 25) comment = comment.substring(0, 25);
		Object[] row = { load, bulletString, powderString, powderMeasureSetting,primerString, caseString,overAllLenthString,comment };
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
	private class ReloadTabMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			int row = componentTable.getSelectedRow();

			Reload load = (Reload) componentTable.getValueAt(row, 0);
			
			if (e.getClickCount() == 2) {
				
				//System.out.println("ID: " + load.getId());

				if (ReloadsDialog.openUpdateDialog(browser.getFrame(), "Update Load", load, browser.getFactory())) {
					browser.saveLoad(load);
					tableModel.setValueAt(load.getCartridge().getShortName(),row,1);
					tableModel.setValueAt(load.getBullet().getShortName(),row,2);
					String powderString = load.getPowderCharge() + "gr " + load.getPowder().getShortName();
					tableModel.setValueAt(powderString,row,3);
					tableModel.setValueAt(load.getPowderMeasureSetting(),row,4);
					tableModel.setValueAt(load.getPrimer().getShortName(),row,5);
					tableModel.setValueAt(load.getCasing().getShortName(),row,6);
					tableModel.setValueAt(load.getOverAllLength(),row,7);
					tab.validate();
				}

			}
			else if (SwingUtilities.isRightMouseButton(e) ){
				JOptionPane.showMessageDialog(null, "Open Contextual Menu"
						, "CONTEXTUAL MENU"
						, JOptionPane.INFORMATION_MESSAGE);
				}
				menu.show(browser.frame,e.getX() + 10,e.getY() - 20);
				menu.setLoad(load);
				menu.setVisible(true);
			}

		}
	}

