package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.coordgeom.Point;

@JsonDeserialize(builder = Rocket.Builder.class)
public class Rocket {
	private final int rocketID;
	private final Point position;
	private final String owner;

	private Rocket(Builder builder) {
		this.rocketID = builder.rocketID;
		this.position = new Point(builder.rocketY, builder.rocketX);
		this.owner = builder.owner;
	}

	public int getRocketID() {
		return rocketID;
	}

	public Point getPosition() {
		return position;
	}

	public String getOwner() {
		return owner;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Rocket)) {
			return false;
		}
		Rocket castOther = (Rocket) other;
		return Objects.equals(rocketID, castOther.rocketID) && Objects.equals(position, castOther.position)
				&& Objects.equals(owner, castOther.owner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rocketID, position, owner);
	}

	@Override
	public String toString() {
		return "Rocket [rocketID=" + rocketID + ", position=" + position + ", owner=" + owner + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private int rocketID;
		private double rocketX;
		private double rocketY;
		private String owner;

		private Builder() {
		}

		public Builder withRocketID(int rocketID) {
			this.rocketID = rocketID;
			return this;
		}

		public Builder withRocketX(double rocketX) {
			this.rocketX = rocketX;
			return this;
		}

		public Builder withRocketY(double rocketY) {
			this.rocketY = rocketY;
			return this;
		}

		public Builder withOwner(String owner) {
			this.owner = owner;
			return this;
		}

		public Rocket build() {
			return new Rocket(this);
		}
	}

}
