package loxon2018.IDunno.dto;

public class Ship {
	private int shipID;
	private double shipX;
	private double shipY;
	private boolean shieldIsActivated;
	private boolean isUpgraded;
	private String owner;

	public int getShipID() {
		return shipID;
	}

	public void setShipID(int shipID) {
		this.shipID = shipID;
	}

	public double getShipX() {
		return shipX;
	}

	public void setShipX(double shipX) {
		this.shipX = shipX;
	}

	public double getShipY() {
		return shipY;
	}

	public void setShipY(double shipY) {
		this.shipY = shipY;
	}

	public boolean isShieldIsActivated() {
		return shieldIsActivated;
	}

	public void setShieldIsActivated(boolean shieldIsActivated) {
		this.shieldIsActivated = shieldIsActivated;
	}

	public boolean isUpgraded() {
		return isUpgraded;
	}

	public void setUpgraded(boolean isUpgraded) {
		this.isUpgraded = isUpgraded;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
