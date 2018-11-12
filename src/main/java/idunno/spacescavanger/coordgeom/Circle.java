package idunno.spacescavanger.coordgeom;

public class Circle {

	private final Point center;
	private final int radius;

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Point getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}

}
