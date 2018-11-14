package idunno.spacescavanger.strategy;

import static java.util.function.Function.identity;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import idunno.spacescavanger.coordgeom.Circle;
import idunno.spacescavanger.coordgeom.Line;
import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Meteorite;
import idunno.spacescavanger.dto.Rocket;
import idunno.spacescavanger.dto.Ship;

public class DummyFeriDummyStrat extends Strategy {

	private static final String OUR_NAME = "idunno";

    public DummyFeriDummyStrat(Game game) {
		super(game);
	}

	public GameResponse doMove(GameState gameStatus) {
		Point ourPos = gameStatus.getIdunnoShip().getPosition();
		Point targetPos = new Point(100, 100);
		double maxPoint = 0.0;
		for (Meteorite meteor : gameStatus.getMeteoriteStates()) {
			double score = meteor.getMeteoriteRadius()
					/ CommonMethods.distanceBetweenTwoPoint(ourPos, meteor.getPosition());
			if (score > maxPoint) {
				targetPos = meteor.getPosition();
				maxPoint = score;
			}
		}
//		List<Meteorite> highestPointsMeteorites = CommonMethods
//				.getHighestPointsMeteorites(gameStatus.getMeteoriteStates());
//		Optional<Point> closestMeteoritePos = CommonMethods.getClosestMeteoritePos(highestPointsMeteorites,
//				shipsByOwner.get("idunno").getPosition());
		Optional<Point> closestMeteoritePosToEnemy = CommonMethods
				.getClosestMeteoritePos(gameStatus.getMeteoriteStates(), gameStatus.getEnemyShip().getPosition());
		Optional<Point> targetRocket = getTarget(gameStatus, closestMeteoritePosToEnemy);
		int score = gameStatus.getStandings().stream()
		    .filter(standing -> standing.getUserID().equals(OUR_NAME))
		    .findAny()
		    .get()
		    .getScore();
		return GameResponse.builder().withUpgraded(score >= game.getUpgradeScore())
		        .withShipMoveToPosition(targetPos).withRocketMoveToPosition(targetRocket).withShieldIsActivated(shouldTurnOnShield(gameStatus)).build();
	}

	private Optional<Point> getTarget(GameState gameStatus,
			Optional<Point> closestMeteoritePosToEnemy) {
		Optional<Point> target;
		if (willHitTarget(gameStatus.getIdunnoShip().getPosition(), gameStatus.getEnemyShip().getPosition(),
				gameStatus)) {
			target = Optional.of(gameStatus.getEnemyShip().getPosition());
		} else if (willHitTarget(gameStatus.getIdunnoShip().getPosition(), closestMeteoritePosToEnemy.orElse(null),
				gameStatus)) {
			target = closestMeteoritePosToEnemy;
		} else {
			target = Optional.empty();
		}
		return target;
	}

	@Override
	protected GameResponse suggestFirstMove(GameState gameStatus) {
		return doMove(gameStatus);
	}

	@Override
	protected GameResponse suggestMove(GameState lastState, GameState gameStatus) {
		return doMove(gameStatus);
	}

	private boolean willHitTarget(Point source, Point target, GameState state) {
		if (source == null || target == null
				|| CommonMethods.distanceBetweenTwoPoint(source, target) > game.getRocketRange()) {
			return false;
		}
		Line path = new Line(source, target, game.getRocketRange());
		for (Meteorite meteor : state.getMeteoriteStates()) {
			if (CommonMethods.isIntersect(path, new Circle(meteor.getPosition(), meteor.getMeteoriteRadius()))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean shouldTurnOnShield(GameState gameStatus) {
	    Point ourPosition = gameStatus.getIdunnoShip().getPosition();
	    return gameStatus.getRocketStates().stream()
	        .filter(rocket -> !OUR_NAME.equalsIgnoreCase(rocket.getOwner()))
	        .filter(rocket -> isRocketAboutToExplode(gameStatus, rocket, ourPosition))
	        .map(rocket -> new Circle(rocket.getPosition(), game.getRocketExplosionRadius()))
	        .anyMatch(circle -> CommonMethods.isInside(ourPosition, circle, 2.));
	}
	
	private boolean isRocketAboutToExplode(GameState gameStatus, Rocket rocket, Point ourPosition) {
	    Line rocketPath = gameStatus.getRocketPaths().get(rocket.getRocketID());
	    if (rocketPath == null) return false;
	    boolean aboutToReachEndOfPath = CommonMethods.distanceBetweenTwoPoint(rocketPath.getEndPoint(), rocket.getPosition()) <= 20.;
	    boolean aboutToHitUs = CommonMethods.distanceBetweenTwoPoint(ourPosition, rocket.getPosition()) <= ((double) game.getRocketExplosionRadius() / 3.);
	    boolean aboutToHitMeteorite = gameStatus.getMeteoriteStates().stream()
	        .map(meteorite -> new Circle(meteorite.getPosition(), meteorite.getMeteoriteRadius()))
	        .filter(circle -> CommonMethods.isIntersect(rocketPath, circle))
	        .anyMatch(circle -> CommonMethods.distanceBetweenTwoPoint(rocket.getPosition(), circle.getCenter()) < circle.getRadius() + 2.);
        return aboutToHitMeteorite || aboutToReachEndOfPath || aboutToHitUs;
	}
}