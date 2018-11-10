package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Rocket.Builder.class)
public class Rocket {
	private final int rocketID;
	private final double rocketX;
	private final double rocketY;
	private final String owner;

	private Rocket(Builder builder) {
		this.rocketID = builder.rocketID;
		this.rocketX = builder.rocketX;
		this.rocketY = builder.rocketY;
		this.owner = builder.owner;
	}

	public int getRocketID() {
		return rocketID;
	}

	public double getRocketX() {
		return rocketX;
	}

	public double getRocketY() {
		return rocketY;
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
		return Objects.equals(rocketID, castOther.rocketID) && Objects.equals(rocketX, castOther.rocketX)
				&& Objects.equals(rocketY, castOther.rocketY) && Objects.equals(owner, castOther.owner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rocketID, rocketX, rocketY, owner);
	}

	@Override
	public String toString() {
		return "Rocket [rocketID=" + rocketID + ", rocketX=" + rocketX + ", rocketY=" + rocketY + ", owner=" + owner
				+ "]";
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
