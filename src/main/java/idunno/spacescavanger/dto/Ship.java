package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Ship.Builder.class)
public class Ship {
	private final int shipID;
	private final double shipX;
	private final double shipY;
	private final boolean shieldIsActivated;
	@JsonProperty("isUpgraded")
	private final boolean upgraded;

	private Ship(Builder builder) {
		this.shipID = builder.shipID;
		this.shipX = builder.shipX;
		this.shipY = builder.shipY;
		this.shieldIsActivated = builder.shieldIsActivated;
		this.upgraded = builder.upgraded;
	}

	public int getShipID() {
		return shipID;
	}

	public double getShipX() {
		return shipX;
	}

	public double getShipY() {
		return shipY;
	}

	public boolean isShieldIsActivated() {
		return shieldIsActivated;
	}

	public boolean isUpgraded() {
		return upgraded;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		Ship castOther = (Ship) other;
		return Objects.equals(shipID, castOther.shipID) && Objects.equals(shipX, castOther.shipX)
				&& Objects.equals(shipY, castOther.shipY)
				&& Objects.equals(shieldIsActivated, castOther.shieldIsActivated)
				&& Objects.equals(upgraded, castOther.upgraded);
	}

	@Override
	public int hashCode() {
		return Objects.hash(shipID, shipX, shipY, shieldIsActivated, upgraded);
	}

	@Override
	public String toString() {
		return "Ship [shipID=" + shipID + ", shipX=" + shipX + ", shipY=" + shipY + ", shieldIsActivated="
				+ shieldIsActivated + ", upgraded=" + upgraded + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private int shipID;
		private double shipX;
		private double shipY;
		private boolean shieldIsActivated;
		private boolean upgraded;

		private Builder() {
		}

		public Builder withShipID(int shipID) {
			this.shipID = shipID;
			return this;
		}

		public Builder withShipX(double shipX) {
			this.shipX = shipX;
			return this;
		}

		public Builder withShipY(double shipY) {
			this.shipY = shipY;
			return this;
		}

		public Builder withShieldIsActivated(boolean shieldIsActivated) {
			this.shieldIsActivated = shieldIsActivated;
			return this;
		}

		public Builder withUpgraded(boolean upgraded) {
			this.upgraded = upgraded;
			return this;
		}

		public Ship build() {
			return new Ship(this);
		}
	}

}
