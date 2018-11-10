package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Meteorite.Builder.class)
public class Meteorite {
	private final int meteoriteRadius;
	private final int meteoriteID;
	private final double meteoriteX;
	private final double meteoriteY;

	private Meteorite(Builder builder) {
		this.meteoriteRadius = builder.meteoriteRadius;
		this.meteoriteID = builder.meteoriteID;
		this.meteoriteX = builder.meteoriteX;
		this.meteoriteY = builder.meteoriteY;
	}

	public int getMeteoriteRadius() {
		return meteoriteRadius;
	}


	public int getMeteoriteID() {
		return meteoriteID;
	}

	public double getMeteoriteX() {
		return meteoriteX;
	}

	public double getMeteoriteY() {
		return meteoriteY;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Meteorite)) {
			return false;
		}
		Meteorite castOther = (Meteorite) other;
		return Objects.equals(meteoriteRadius, castOther.meteoriteRadius)
				&& Objects.equals(meteoriteID, castOther.meteoriteID)
				&& Objects.equals(meteoriteX, castOther.meteoriteX) && Objects.equals(meteoriteY, castOther.meteoriteY);
	}

	@Override
	public int hashCode() {
		return Objects.hash(meteoriteRadius, meteoriteID, meteoriteX, meteoriteY);
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public String toString() {
		return "Meteorite [meteoriteRadius=" + meteoriteRadius + ", meteoriteID=" + meteoriteID + ", meteoriteX="
				+ meteoriteX + ", meteoriteY=" + meteoriteY + "]";
	}

	public static final class Builder {
		private int meteoriteRadius;
		private int meteoriteID;
		private double meteoriteX;
		private double meteoriteY;

		private Builder() {
		}

		public Builder withMeteoriteRadius(int meteoriteRadius) {
			this.meteoriteRadius = meteoriteRadius;
			return this;
		}

		public Builder withMeteoriteID(int meteoriteID) {
			this.meteoriteID = meteoriteID;
			return this;
		}

		public Builder withMeteoriteX(double meteoriteX) {
			this.meteoriteX = meteoriteX;
			return this;
		}

		public Builder withMeteoriteY(double meteoriteY) {
			this.meteoriteY = meteoriteY;
			return this;
		}

		public Meteorite build() {
			return new Meteorite(this);
		}
	}


}
