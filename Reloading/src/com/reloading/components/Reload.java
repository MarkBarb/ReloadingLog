package com.reloading.components;

import com.reloading.Constants;

public class Reload extends Load {
	public Reload() {
		super();
		name = "Reload";
		shortName = "Reload";
	}

	public Reload(int id) {
		super(id);

		// have to set these so saveComponent method does not error out.
		name = "Reload";
		shortName = "Reload";
	}

	private Bullet bullet = null;
	private Case casing = null;
	private Powder powder = null;
	private float powderCharge = 0.0f;
	private float overAllLength = 0.0f;
	private String powderMeasureSetting = "";
	private Primer primer = null;

	public Reload(int id, Bullet bullet, Case casing, Powder powder, float powderCharge, Primer primer) {
		super(id);
		// have to set these so saveComponent method does not error out.
		name = "Reload";
		shortName = "Reload";
		this.bullet = bullet;
		this.casing = casing;
		this.powder = powder;
		this.powderCharge = powderCharge;
		this.primer = primer;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public CastBullet getCastBullet() {
		String className = bullet.getClass().getName();
		switch (className) {
		case Constants.CASTBULLET_CLASSNAME:
			return (CastBullet) bullet;
		default:
			return null;
		}
	}

	public int getBulletId() {
		if (bullet == null) {
			return -1;
		}
		return bullet.getId();
	}

	public int getCasingId() {
		if (casing == null) {
			return -1;
		}
		return casing.getId();
	}

	public Case getCasing() {
		return casing;
	}

	public float getOverAllLength() {
		return overAllLength;
	}

	public Powder getPowder() {
		return powder;
	}

	public int getPowderId() {
		if (powder == null) {
			return -1;
		}
		return powder.getId();
	}

	public float getPowderCharge() {
		return powderCharge;
	}

	public String getPowderMeasureSetting() {
		return powderMeasureSetting;
	}

	public Primer getPrimer() {
		return primer;
	}

	public int getPrimerId() {
		if (primer == null) {
			return -1;
		}
		return primer.getId();
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}
	
	public void setCasing(Case casing) {
		this.casing = casing;
	}

	public void setCastBullet(CastBullet bullet) {
		this.bullet = bullet;
	}


	public void setOverAllLength(String overAllLength) {
		this.overAllLength = Float.parseFloat(overAllLength);
	}
	
	public void setOverAllLength(float overAllLength) {
		this.overAllLength = overAllLength;
	}

	public void setPowder(Powder powder) {
		this.powder = powder;
	}

	public void setPowderCharge(String powderCharge) {
		float fPowderCharge = Float.parseFloat(powderCharge);
		this.powderCharge = fPowderCharge;
	}

	public void setPowderCharge(float powderCharge) {
		this.powderCharge = powderCharge;
	}

	public void setPowderMeasureSetting(String powderMeasureSetting) {
		this.powderMeasureSetting = powderMeasureSetting;
	}

	public void setPrimer(Primer primer) {
		this.primer = primer;
	}
	
	public String toString(){
		return this.getCartridge().toString();
	}

}
