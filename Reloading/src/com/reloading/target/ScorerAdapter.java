package com.reloading.target;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import com.reloading.target.Scorer;

public class ScorerAdapter extends MouseAdapter implements ActionListener {
	private Scorer scorer;
	protected void setScorer (Scorer nScorer){
		scorer = nScorer;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		switch (actionCommand){
		case Scorer.FILE:
			System.out.println(Scorer.ADD_COMPONENT + " Button Pressed");
			scorer.loadImageFile();
			break;
		case Scorer.OPEN:
			System.out.println(Scorer.OPEN + " menu Pressed");
			scorer.loadImageFile();
			break;
				
		default:  
			System.out.println("What the heck happened");
			break;
		}
	}

}


