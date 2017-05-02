package com.reloading.testing;

import java.util.ArrayList;
import java.util.HashMap;

import com.reloading.components.Firearm;
import com.reloading.components.Load;
import com.reloading.components.Reload;

public class Test {
	
	private Firearm firearm;
	private int id = 0;
	private Load load;
	
	protected ArrayList <Shot> shots;
	
	public Test() {
		super();
		shots = new ArrayList <Shot>();
	}
	
	public Test(int id, Load load, Firearm firearm ) {
		super();
		shots = new ArrayList <Shot>();
		this.load = load;
		this.firearm = firearm;
		this.id = id;
	}
	
	public void addShot(Shot shot){
		if (shots == null){
			shots = new  ArrayList <Shot>();
		}
		int index = shots.size();
		shots.add(index, shot);
	}
	
	
	public Firearm getFirearm() {
		return firearm;
	}

	public int getId() {
		return id;
	}

	public Load getLoad() {
		return load;
	}

	public Shot getShot(int shotIndex){
		return shots.get(shotIndex);
	}
	
	public int getShotCount(){
		return shots.size();
	}
	
	public ArrayList<Shot> getShotsList() {
		return shots;
	}

	public void setFirearm(Firearm firearm) {
		this.firearm = firearm;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLoad(Reload load) {
		this.load = load;
	}

	public void setShotList(ArrayList<Shot> shots) {
		this.shots = shots;
	}

	
	


	
	
}
