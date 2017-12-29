package com.reloading.browser;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;

import com.reloading.Constants;
import com.reloading.components.Cartridge;
import com.reloading.components.Component;
import com.reloading.events.ReloadingEvent;
import com.reloading.exceptions.ReloadingException;

public abstract class ReloadingLogTab extends JPanel {
	protected JScrollPane scrollPane;
	protected JTable componentTable;
	protected DefaultTableModel tableModel;
	protected ReloadingLogBrowser browser;
	protected ReloadingLogTab tab;
	public ReloadingLogTab(ReloadingLogBrowser browser) {
		super(new BorderLayout());
		tab = this;
		this.browser = browser;
	}

	public ReloadingLogTab(ReloadingLogBrowser browser, LayoutManager layout) {
		super(layout);
		tab = this;
		this.browser = browser;
	}

	private void buildScrollPane(){
		//System.out.println("building ScrollPane");
		scrollPane = new JScrollPane();
		scrollPane.setLayout(new ScrollPaneLayout());
		this.add(scrollPane,BorderLayout.CENTER);
	}
	
	//public abstract void buildDisplayTable(ArrayList <Component> components) ;
	
	public abstract void handleReloadingEvent(ReloadingEvent event) throws ReloadingException;
	
	
}
