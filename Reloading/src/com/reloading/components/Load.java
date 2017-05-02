package com.reloading.components;

public class Load  extends Component implements Comparable<Load> {
	//private int id = -1;
	private String comments = "";
	private Cartridge cartridge = null;
	
	public Load() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCartridgeId() {
		if (cartridge == null){
			return -1;
		}
		return cartridge.getId();
	}

	public Cartridge getCartridge() {
		return cartridge;
	}
	
	public String getComments() {
		return comments;
	}

	public void setCartridge(Cartridge cartridge) {
		this.cartridge = cartridge;
	}

	public void setComments(String comments){
		this.comments = comments;
	}
	
	public Load(int id) {
		this.id = id;
	}
	
	@Override
    public int compareTo(Load load) {
		int rtnVal = cartridge.compareTo(load.getCartridge());
		return rtnVal;
    }
	
}
