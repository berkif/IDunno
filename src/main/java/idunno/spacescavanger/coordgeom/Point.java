package idunno.spacescavanger.coordgeom;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Objects;

public class Point {
	private final double y, x;

	public Point(double y, double x) {
		this.y = y;
		this.x = x;
	}

	public double y() {
		return y;
	}

	public double x() {
		return x;
	}

	public double distance(Point other) {
		return sqrt(pow((x - other.x()), 2) + pow((y - other.y()), 2));
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Point)) {
			return false;
		}
		Point castOther = (Point) other;
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
