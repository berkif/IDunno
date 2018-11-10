package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GameResponse.Builder.class)
public class GameResponse {
	private final double shipMoveToX;
	private final double shipMoveToY;
	private final boolean shieldIsActivated;
	private final Double rocketMoveToX;
	private final Double rocketMoveToY;
	@JsonProperty("isUpgraded")
	private final boolean upgraded;

	private GameResponse(Builder builder) {
		this.shipMoveToX = builder.shipMoveToX;
		this.shipMoveToY = builder.shipMoveToY;
		this.shieldIsActivated = builder.shieldIsActivated;
		this.rocketMoveToX = builder.rocketMoveToX;
		this.rocketMoveToY = builder.rocketMoveToY;
		this.upgraded = builder.upgraded;
	}

	public static Builder builder() {
		return new Builder();
	}

	public double getShipMoveToX() {
		return shipMoveToX;
	}

	public double getShipMoveToY() {
		return shipMoveToY;
	}

	public boolean isShieldIsActivated() {
		return shieldIsActivated;
	}

	public Double getRocketMoveToX() {
		return rocketMoveToX;
	}

	public Double getRocketMoveToY() {
		return rocketMoveToY;
	}

	public boolean isUpgraded() {
		return upgraded;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof GameResponse)) {
			return false;
		}
		GameResponse castOther = (GameResponse) other;
		return Objects.equals(shipMoveToX, castOther.shipMoveToX) && Objects.equals(shipMoveToY, castOther.shipMoveToY)
				&& Objects.equals(shieldIsActivated, castOther.shieldIsActivated)
				&& Objects.equals(rocketMoveToX, castOther.rocketMoveToX)
				&& Objects.equals(rocketMoveToY, castOther.rocketMoveToY)
				&& Objects.equals(upgraded, castOther.upgraded);
	}

	@Override
	public int hashCode() {
		return Objects.hash(shipMoveToX, shipMoveToY, shieldIsActivated, rocketMoveToX, rocketMoveToY, upgraded);
	}

	@Override
	public String toString() {
		return "GameResponse [shipMoveToX=" + shipMoveToX + ", shipMoveToY=" + shipMoveToY + ", shieldIsActivated="
				+ shieldIsActivated + ", rocketMoveToX=" + rocketMoveToX + ", rocketMoveToY=" + rocketMoveToY
				+ ", upgraded=" + upgraded + "]";
	}

	public static final class Builder {
		private double shipMoveToX;
		private double shipMoveToY;
		private boolean shieldIsActivated;
		private Double rocketMoveToX;
		private Double rocketMoveToY;
		private boolean upgraded;

		private Builder() {
		}

		public Builder withShipMoveToX(double shipMoveToX) {
			this.shipMoveToX = shipMoveToX;
			return this;
		}

		public Builder withShipMoveToY(double shipMoveToY) {
			this.shipMoveToY = shipMoveToY;
			return this;
		}

		public Builder withShieldIsActivated(boolean shieldIsActivated) {
			this.shieldIsActivated = shieldIsActivated;
			return this;
		}

		public Builder withRocketMoveToX(Double rocketMoveToX) {
			this.rocketMoveToX = rocketMoveToX;
			return this;
		}

		public Builder withRocketMoveToY(Double rocketMoveToY) {
			this.rocketMoveToY = rocketMoveToY;
			return this;
		}

		public Builder withUpgraded(boolean upgraded) {
			this.upgraded = upgraded;
			return this;
		}

		public GameResponse build() {
			return new GameResponse(this);
		}
	}
}
