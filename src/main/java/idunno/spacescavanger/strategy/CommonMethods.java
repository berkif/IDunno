package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByDistance;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import idunno.spacescavanger.coordgeom.Circle;
import idunno.spacescavanger.coordgeom.Line;
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

	public static boolean isIntersect(Line a, Line b) {
		double x1 = a.getStartPoint().x();
		double x2 = a.getEndPoint().x();
		double x3 = b.getStartPoint().x();
		double x4 = b.getEndPoint().x();
		double y1 = a.getStartPoint().y();
		double y2 = a.getEndPoint().y();
		double y3 = b.getStartPoint().y();
		double y4 = b.getEndPoint().y();
		return Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public static boolean isIntersect(Line line, Circle circle) {
		double x1 = line.getStartPoint().x();
		double x2 = line.getEndPoint().x();
		double y1 = line.getStartPoint().y();
		double y2 = line.getEndPoint().y();
		return Line2D.ptSegDist(x1, y1, x2, y2, circle.getCenter().x(), circle.getCenter().y()) < circle.getRadius();
	}

	public static double getDistance(Line line, Point point) {
		double x1 = line.getStartPoint().x();
		double x2 = line.getEndPoint().x();
		double y1 = line.getStartPoint().y();
		double y2 = line.getEndPoint().y();
		return Line2D.ptSegDist(x1, y1, x2, y2, point.x(), point.y());
	}

}
