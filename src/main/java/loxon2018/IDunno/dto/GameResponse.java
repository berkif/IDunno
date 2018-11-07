package loxon2018.IDunno.dto;

public class GameResponse {
	private double shipMoveToX;
	private double shipMoveToY;
	private boolean shieldIsActivated;
	private Double rocketMoveToX;
	private Double rocketMoveToY;
	private boolean isUpgraded;

	public double getShipMoveToX() {
		return shipMoveToX;
	}

	public void setShipMoveToX(double shipMoveToX) {
		this.shipMoveToX = shipMoveToX;
	}

	public double getShipMoveToY() {
		return shipMoveToY;
	}

	public void setShipMoveToY(double shipMoveToY) {
		this.shipMoveToY = shipMoveToY;
	}

	public boolean isShieldIsActivated() {
		return shieldIsActivated;
	}

	public void setShieldIsActivated(boolean shieldIsActivated) {
		this.shieldIsActivated = shieldIsActivated;
	}

	public Double getRocketMoveToX() {
		return rocketMoveToX;
	}

	public void setRocketMoveToX(Double rocketMoveToX) {
		this.rocketMoveToX = rocketMoveToX;
	}

	public Double getRocketMoveToY() {
		return rocketMoveToY;
	}

	public void setRocketMoveToY(Double rocketMoveToY) {
		this.rocketMoveToY = rocketMoveToY;
	}

	public boolean isUpgraded() {
		return isUpgraded;
	}

	public void setUpgraded(boolean isUpgraded) {
		this.isUpgraded = isUpgraded;
	}

}
