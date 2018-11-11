package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.coordgeom.Point;

@JsonDeserialize(builder = Meteorite.Builder.class)
public class Meteorite {
	private final int meteoriteRadius;
	private final int meteoriteID;
	private final Point position;

	private Meteorite(Builder builder) {
		this.meteoriteRadius = builder.meteoriteRadius;
		this.meteoriteID = builder.meteoriteID;
		this.position = new Point(builder.meteoriteY, builder.meteoriteX);
	}

	public int getMeteoriteRadius() {
		return meteoriteRadius;
	}

	public int getMeteoriteID() {
		return meteoriteID;
	}

	public Point getPosition() {
		return position;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Meteorite)) {
			return false;
		}
		Meteorite castOther = (Meteorite) other;
		return Objects.equals(meteoriteRadius, castOther.meteoriteRadius)
				&& Objects.equals(meteoriteID, castOther.meteoriteID) && Objects.equals(position, castOther.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(meteoriteRadius, meteoriteID, position);
	}

	@Override
	public String toString() {
		return "Meteorite [meteoriteRadius=" + meteoriteRadius + ", meteoriteID=" + meteoriteID + ", position="
				+ position + "]";
	}

	public static Builder builder() {
		return new Builder();
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
