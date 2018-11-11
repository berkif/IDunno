package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByDistance;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Meteorite;

public class CommonMethods {

	public static List<Meteorite> getHighestPointsMeteorites(List<Meteorite> meteoriteStates) {
		List<Meteorite> result = new ArrayList<>();
		int max = 0;
		for (Meteorite meteorite : meteoriteStates) {
			if (meteorite.getMeteoriteRadius() > max) {
				max = meteorite.getMeteoriteRadius();
				result.clear();
				result.add(meteorite);
			} else if (meteorite.getMeteoriteRadius() == max) {
				result.add(meteorite);
			}
		}
		return result;
	}

	public static Optional<Point> getClosestMeteoritePos(List<Meteorite> meteoriteStates, Point position) {
		return meteoriteStates.stream().map(Meteorite::getPosition).min(compareByDistance(position));
	}

	public static double distanceBetweenTwoPoint(Point a, Point b) {
		return sqrt(pow((a.x() - b.x()), 2) + pow((a.y() - b.y()), 2));
	}

}
