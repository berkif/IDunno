package idunno.spacescavanger.strategy;

import static java.lang.Double.compare;

import java.util.Comparator;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Meteorite;

public class Comparators {

	public static Comparator<Point> compareByDistance(Point from) {
		return (first, second) -> compare(from.distance(first), from.distance(second));
	}
	
	public static Comparator<Meteorite> compareByScore() {
		return (first, second) -> compare(
				first.getMeteoriteRadius() / first.getDistance(),
				second.getMeteoriteRadius()	/ second.getDistance());
	}
}
