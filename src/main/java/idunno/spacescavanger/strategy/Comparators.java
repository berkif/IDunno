package idunno.spacescavanger.strategy;

import static java.lang.Double.compare;

import java.util.Comparator;

import idunno.spacescavanger.dto.Meteorite;

public class Comparators {

	static Comparator<Position> compareByDistance(Position from) {
		return (first, second) -> compare(from.distance(first), from.distance(second));
	}
}
