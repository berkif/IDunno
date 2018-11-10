package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.strategy.Position;

@JsonDeserialize(builder = Ship.Builder.class)
public class Ship {
	private final int shipID;
	private final Position position;
	private final boolean shieldIsActivated;
	private final boolean upgraded;
	private final String owner;

	private Ship(Builder builder) {
		this.shipID = builder.shipID;
		this.shieldIsActivated = builder.shieldIsActivated;
		this.upgraded = builder.upgraded;
		this.owner = builder.owner;
		this.position = new Position(builder.shipY, builder.shipX);
	}

	public Position getPosition() {
		return position;
	}

	public int getShipID() {
		return shipID;
	}

	public boolean isShieldIsActivated() {
		return shieldIsActivated;
	}

	public boolean isUpgraded() {
		return upgraded;
	}

	public String getOwner() {
		return owner;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		Ship castOther = (Ship) other;
		return Objects.equals(shipID, castOther.shipID) && Objects.equals(position, castOther.position)
				&& Objects.equals(shieldIsActivated, castOther.shieldIsActivated)
				&& Objects.equals(upgraded, castOther.upgraded) && Objects.equals(owner, castOther.owner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(shipID, position, shieldIsActivated, upgraded, owner);
	}

	@Override
	public String toString() {
		return "Ship [shipID=" + shipID + ", position=" + position + ", shieldIsActivated=" + shieldIsActivated
				+ ", upgraded=" + upgraded + ", owner=" + owner + "]";
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
		private String owner;

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

		public Builder withIsUpgraded(boolean upgraded) {
			this.upgraded = upgraded;
			return this;
		}

		public Builder withOwner(String owner) {
			this.owner = owner;
			return this;
		}

		public Ship build() {
			return new Ship(this);
		}
	}
}
