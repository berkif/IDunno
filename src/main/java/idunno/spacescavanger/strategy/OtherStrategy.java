package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByScore;

import java.util.Optional;
import java.util.function.Predicate;

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
		Optional<Point> moveToPosition = Optional.empty();
		Ship enemyShip = currentState.getEnemy(BOT_NAME);
		Ship idunnoShip = currentState.getIdunnoShip();
		Point enemyPos = enemyShip.getPosition();
		if (currentState.getMeteoriteStates().size() == 1
				|| currentState.getMeteoriteStates().stream().filter(coserToUsPredicate(idunnoShip, enemyShip)).allMatch(m -> m.getMeteoriteRadius() < 30)) {
		    moveToPosition = Optional.of(new Point(enemyPos.x() - (double) game.getRocketExplosionRadius() *2., enemyPos.y() - (double) game.getRocketExplosionRadius() *2.));
		} else {
				moveToPosition = currentState.getMeteoriteStates()
						.stream()
						.max(compareByScore()) // teljesen ugyan az mint a régiben, csak comparatorral
						.map(Meteorite::getPosition);
//			}
		}
		return moveToPosition;
	}

	private boolean weAreInDanger(GameState currentState) {
		// az a baj, hogy a mozgásunk nincs belekalkulálva igy akkor is kiírja, ha elmozgunk majd igy is ugy is
		Circle circle = new Circle(currentState.getIdunnoShip().getPosition(), game.getRocketExplosionRadius());
		return currentState.getRocketStates().stream()
			.filter(r -> !r.getOwner().equals(OUR_NAME))
			.map(r -> currentState.getRocketPaths().get(r.getRocketID()))
			.anyMatch(path -> CommonMethods.isIntersect(path, circle));
	}

	private Point fallBackMove() {
		return new Point(0, 0);
	}
	private Predicate<Meteorite> coserToUsPredicate(Ship ourShip, Ship enemyShip) {
		return meteorite -> {
			return meteorite.getDistanceFromUs() / ourShip.getSpeed() < meteorite.getDistanceFromEnemy(BOT_NAME) / enemyShip.getSpeed(); 
		};
	}
	private Optional<Point> getRocketMoveToPosition(GameState lastState, GameState currentState) {
//		if (rocketOnCooldown(currentState.getTimeElapsed())) {
//			return Optional.empty();
//		}
		Optional<Meteorite> closestMeteoritePosToEnemy = CommonMethods
				.getClosestMeteoritePosToEnemy(currentState.getMeteoriteStates(), currentState.getEnemy(BOT_NAME).getPosition());
		return getTarget(currentState, closestMeteoritePosToEnemy, lastState.getEnemy(BOT_NAME));
	}
	private Optional<Point> getTarget(GameState gameStatus,
			Optional<Meteorite> closestMeteoritePosToEnemy, Ship enemyShipLastState) {
		Ship botShip = gameStatus.getEnemy(BOT_NAME);
		Optional<Point> target = Optional.empty();
			Point targetVelocity = calculateVelocity(enemyShipLastState, botShip);
			if (botShip.getPosition().distance(gameStatus.getIdunnoShip().getPosition()) < game.getRocketRange()) {
				target = getShootTargetPosition(1, gameStatus.getIdunnoShip().getPosition(), botShip.getPosition(), targetVelocity, closestMeteoritePosToEnemy)
						.filter(t -> willHitTarget(gameStatus.getIdunnoShip().getPosition(), t,
							gameStatus, closestMeteoritePosToEnemy))
						;
			}
			if (!target.isPresent() && gameStatus.getMeteoriteStates().size() > 1 && closestMeteoritePosToEnemy.get().getDistanceFromUs() < game.getRocketRange()
					&& willHitTarget(gameStatus.getIdunnoShip().getPosition(), closestMeteoritePosToEnemy.map(Meteorite::getPosition).orElse(null),
						gameStatus, closestMeteoritePosToEnemy)) {
				target = closestMeteoritePosToEnemy.map(Meteorite::getPosition);
			}
			
			
		return target;
	}
	private boolean willHitTarget(Point source, Point target, GameState state, Optional<Meteorite> excludedMeteor) {
		if (source == null || target == null
				|| CommonMethods.distanceBetweenTwoPoint(source, target) > game.getRocketRange()) {
			return false;
		}
		Line path = new Line(source, target);
		return state.getMeteoriteStates().stream()
			.filter(m -> !excludedMeteor.filter(excluded -> excluded.getDistanceFromUs() > excluded.getDistanceFromEnemy(BOT_NAME)).isPresent() || m.getMeteoriteID() != excludedMeteor.get().getMeteoriteID())
			.allMatch(notInTheWay(path));
//		for (Meteorite meteor : state.getMeteoriteStates()) {
//			if (CommonMethods.isIntersect(path, new Circle(meteor.getPosition(), meteor.getMeteoriteRadius()))) {
//				return false;
//			}
//		}
//		return true;
	}
	private Predicate<Meteorite> notInTheWay(Line path) {
		return meteor -> {
			return !CommonMethods.isIntersect(path, new Circle(meteor.getPosition(), meteor.getMeteoriteRadius()));
		};

	}
	private boolean shouldUpgrade(GameState currentState) {
		return currentState.getOurScore() >= game.getUpgradeScore() && currentState.getMeteoriteStates().size() >= 3;
	}

	private boolean shouldTurnOnShield(GameState currentState) {
		 Point ourPosition = currentState.getIdunnoShip().getPosition();
		    return !shieldOnCooldown(currentState.getTimeElapsed()) && currentState.getRocketStates().stream()
		        .filter(rocket -> !OUR_NAME.equalsIgnoreCase(rocket.getOwner()))
		        .filter(rocket -> isRocketAboutToExplode(currentState, rocket, ourPosition))
		        .map(rocket -> new Circle(rocket.getPosition(), game.getRocketExplosionRadius()))
		        .anyMatch(circle -> CommonMethods.isInside(ourPosition, circle, 2.));
	}
	
	private boolean isRocketAboutToExplode(GameState gameStatus, Rocket rocket, Point ourPosition) {
	    Line rocketPath = gameStatus.getRocketPaths().get(rocket.getRocketID());
	    if (rocketPath == null) return false;
	    boolean aboutToReachEndOfPath = CommonMethods.distanceBetweenTwoPoint(rocketPath.getEndPoint(), rocket.getPosition()) <= 20.;
	    boolean aboutToHitUs = CommonMethods.distanceBetweenTwoPoint(ourPosition, rocket.getPosition()) <= ((double) game.getRocketExplosionRadius());
	    boolean aboutToHitMeteorite = gameStatus.getMeteoriteStates().stream()
	        .map(meteorite -> new Circle(meteorite.getPosition(), meteorite.getMeteoriteRadius()))
	        .filter(circle -> CommonMethods.isIntersect(rocketPath, circle))
	        .anyMatch(circle -> CommonMethods.distanceBetweenTwoPoint(rocket.getPosition(), circle.getCenter()) < circle.getRadius() + 2.);
        return aboutToHitMeteorite || aboutToReachEndOfPath || aboutToHitUs;
	}
}
