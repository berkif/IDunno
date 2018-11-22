package idunno.spacescavanger.coordgeom;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Objects;

/**
 * Do not modify!!!
 * 
 * @author Jocó
 *
 */
public class Point {
	private final double y, x;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
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
	public Point substract(Point other) {
        return new Point(x - other.x, y - other.y());
    }
    public Point add(Point other) {
        return new Point(x + other.x, y + other.y());
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
