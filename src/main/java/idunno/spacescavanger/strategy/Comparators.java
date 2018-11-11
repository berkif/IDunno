package idunno.spacescavanger.strategy;

import static java.lang.Double.compare;

import java.util.Comparator;

import idunno.spacescavanger.coordgeom.Point;

public class Comparators {

	public static Comparator<Point> compareByDistance(Point from) {
		return (first, second) -> compare(from.distance(first), from.distance(second));
	}
}
