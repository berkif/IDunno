package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByScore;

import java.util.Optional;

import idunno.spacescavanger.coordgeom.Circle;
import idunno.spacescavanger.coordgeom.Line;
import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Meteorite;
import idunno.spacescavanger.dto.Rocket;
import idunno.spacescavanger.dto.Ship;

public class OtherStrategy extends Strategy {
	public OtherStrategy(Game game) {
		super(game);
	}

	@Override
	protected GameResponse suggestFirstMove(GameState currentState) {
		return GameResponse.builder()
				.withShipMoveToPosition(getShipMoveToPosition(currentState)
												.orElseGet(() -> fallBackMove()))
				.build();
	}

	@Override
	protected GameResponse suggestMove(GameState lastState, GameState currentState) {
		return GameResponse.builder()
				.withShipMoveToPosition(getShipMoveToPosition(currentState)
												.orElseGet(() -> fallBackMove()))
				.withRocketMoveToPosition(getRocketMoveToPosition(lastState, currentState))
				.withShieldIsActivated(shouldTurnOnShield(currentState))
				.withUpgraded(shouldUpgrade(currentState))
				.build();
	}

	private Optional<Point> getShipMoveToPosition(GameState currentState) {
		Optional<Point> moveToPosition;
		if (currentState.getMeteoriteStates().size() == 1) {
		    Point enemyPos = currentState.getEnemyShip().getPosition();
		    moveToPosition = Optional.of(new Point(enemyPos.x() - (double) game.getRocketExplosionRadius() + 5., enemyPos.y() - (double) game.getRocketExplosionRadius() + 5.));
		} else {
			moveToPosition = currentState.getMeteoriteStates()
					.stream()
					.max(compareByScore()) // teljesen ugyan az mint a régiben, csak comparatorral
					.map(Meteorite::getPosition);
		}
		return moveToPosition;
	}

	private Point fallBackMove() {
		return new Point(0, 0);
	}

	private Optional<Point> getRocketMoveToPosition(GameState lastState, GameState currentState) {
		Optional<Point> closestMeteoritePosToEnemy = CommonMethods
				.getClosestMeteoritePos(currentState.getMeteoriteStates(), currentState.getEnemyShip().getPosition());
		return getTarget(currentState, closestMeteoritePosToEnemy, lastState.getEnemyShip());
	}
	private Optional<Point> getTarget(GameState gameStatus,
			Optional<Point> closestMeteoritePosToEnemy, Ship enemyShip) {
		Optional<Point> target;
		if (willHitTarget(gameStatus.getIdunnoShip().getPosition(), gameStatus.getEnemyShip().getPosition(),
				gameStatus)) {
			Point targetVelocity = calculateVelocity(enemyShip, gameStatus.getEnemyShip());
			target = getShootTargetPosition(1, gameStatus.getIdunnoShip().getPosition(), gameStatus.getEnemyShip().getPosition(), targetVelocity);
		} else if (willHitTarget(gameStatus.getIdunnoShip().getPosition(), closestMeteoritePosToEnemy.orElse(null),
				gameStatus)) {
			target = closestMeteoritePosToEnemy;
		} else {
			target = Optional.empty();
		}
		return target;
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
	private boolean shouldUpgrade(GameState currentState) {
		return currentState.getOurScore() >= game.getUpgradeScore() && currentState.getMeteoriteStates().size() >= 3;
	}

	private boolean shouldTurnOnShield(GameState currentState) {
		 Point ourPosition = currentState.getIdunnoShip().getPosition();
		    return currentState.getRocketStates().stream()
		        .filter(rocket -> !OUR_NAME.equalsIgnoreCase(rocket.getOwner()))
		        .filter(rocket -> isRocketAboutToExplode(currentState, rocket, ourPosition))
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
