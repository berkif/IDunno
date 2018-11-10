package idunno.spacescavanger.dto;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.strategy.Position;

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
		this.shipMoveToX = builder.shipMoveToPosition.x();
		this.shipMoveToY = builder.shipMoveToPosition.y();
		this.shieldIsActivated = builder.shieldIsActivated;
		this.rocketMoveToX = builder.rocketMoveToPosition.map(Position::x)
				.orElse(null);
		this.rocketMoveToY = builder.rocketMoveToPosition.map(Position::y)
				.orElse(null);
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
		private Position shipMoveToPosition;
		private boolean shieldIsActivated = false;
		private Optional<Position> rocketMoveToPosition = Optional.empty();
		private boolean upgraded = false;

		private Builder() {
		}

		public Builder withShipMoveToPosition(Position position) {
			this.shipMoveToPosition = position;
			return this;
		}

		public Builder withShieldIsActivated(boolean shieldIsActivated) {
			this.shieldIsActivated = shieldIsActivated;
			return this;
		}

		public Builder withRocketMoveToPosition(Optional<Position> position) {
			this.rocketMoveToPosition = position;
			return this;
		}

		public Builder withRocketMoveToPosition(Position position) {
			this.rocketMoveToPosition = Optional.of(position);
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
