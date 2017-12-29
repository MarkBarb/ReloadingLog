package com.reloading.testing;

import com.reloading.components.Firearm;
import com.reloading.components.Load;
import com.reloading.testing.benchtest.BenchShot;

public class Shot implements Comparable<Shot>{

	private int id;
	private Test test;

	public Shot(){
		super();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Test getTest() {
		return test;
	}
	
	public void setTest(Test test) {
		this.test = test;
	}
	
	public int compareTo(Shot shot) {
		int compId = shot.getId();
		if (id == compId) {
			return 0;
		}
		else if (id < compId){
			return -1;
		}
		return 1;
	}

}
