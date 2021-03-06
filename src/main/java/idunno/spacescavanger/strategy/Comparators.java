package idunno.spacescavanger.strategy;

import static java.lang.Double.compare;

import java.util.Comparator;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Meteorite;

public class Comparators {

	public static Comparator<Point> compareByDistance(Point from) {
		return (first, second) -> compare(from.distance(first), from.distance(second));
	}
	public static Comparator<Meteorite> compareByDistanceFromEnemy(String enemyName) {
		return (first, second) -> compare(first.getDistanceFromEnemy(enemyName), second.getDistanceFromEnemy(enemyName));
	}
	public static Comparator<Meteorite> compareByDistanceFromUs() {
		return (first, second) -> compare(first.getDistanceFromUs(), second.getDistanceFromUs());
	}
	public static Comparator<Meteorite> compareByScore() {
		return (first, second) -> compare(
				first.getMeteoriteRadius() / first.getDistanceFromUs(),
				second.getMeteoriteRadius()	/ second.getDistanceFromUs());
	}
	public static Comparator<Meteorite> compareByScoreWithEnemyDistance(Point enemyPos) {
		return (first, second) -> compare(
				first.getMeteoriteRadius() / (first.getDistanceFromUs() + first.getPosition().distance(enemyPos)),
				second.getMeteoriteRadius()	/ (second.getDistanceFromUs() + first.getPosition().distance(enemyPos)));
	}
}
