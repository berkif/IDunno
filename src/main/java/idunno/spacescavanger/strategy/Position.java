package idunno.spacescavanger.strategy;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Objects;

public class Position {
	private final double y, x;

	public Position(double y, double x) {
		this.y = y;
		this.x = x;
	}

	public double y() {
		return y;
	}

	public double x() {
		return x;
	}

	double distance(Position other) {
		return sqrt(pow((x - other.x()), 2) + pow((y - other.y()), 2));
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Position)) {
			return false;
		}
		Position castOther = (Position) other;
		return Objects.equals(y, castOther.y) && Objects.equals(x, castOther.x);
	}

	@Override
	public int hashCode() {
		return Objects.hash(y, x);
	}

	@Override
	public String toString() {
		return "Position [y=" + y + ", x=" + x + "]";
	}

}
