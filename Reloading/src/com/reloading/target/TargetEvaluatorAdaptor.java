package com.reloading.target;

import com.reloading.browser.ReloadingLogBrowser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TargetEvaluatorAdaptor extends MouseAdapter implements ActionListener {
	private TargetEvaluator targetEvaluator;
	private ReloadingLogBrowser manual;
	
	
	public void setManual(ReloadingLogBrowser manual) {
		this.manual = manual;
	}

	/**
	 * @param targetEvaluator
	 */
	public void setTargetEvaluator(TargetEvaluator targetEvaluator) {
		this.targetEvaluator = targetEvaluator;
	}
	
	public void actionPerformed(ActionEvent e) {
	
	}

	public void  mouseClicked(MouseEvent e) {
		targetEvaluator.handleMouseClicked(e);
   }
}
