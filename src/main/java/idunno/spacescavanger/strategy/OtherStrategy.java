package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByScore;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
import idunno.spacescavanger.dto.Standings;

public class OtherStrategy extends Strategy {
	private Optional<String> hateU = Optional.empty();
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
		Ship idunnoShip = currentState.getIdunnoShip();
		Optional<Rocket> weAreInDanger = weAreInDanger(currentState);
		if (shieldOnCooldown(currentState.getTimeElapsed()) && weAreInDanger.isPresent()) {
			Rocket incomingRocket = weAreInDanger.get();
			Line path = currentState.getRocketPaths().get(incomingRocket.getRocketID());
			boolean wilHitMeteor =	currentState.getMeteoriteStates().stream()
								.filter(m -> m.getDistanceFromUs() < m.getPosition().distance(incomingRocket.getPosition()))
								.anyMatch(m -> CommonMethods.isIntersect(path, new Circle(m.getPosition(), m.getMeteoriteRadius())));
			if (!wilHitMeteor) {
				moveToPosition = getDefensiveMoveList(idunnoShip.getPosition()).stream()
						.max(Comparators.compareByDistance(weAreInDanger.get().getPosition()));
			}
		}
//		if (shieldOnCooldown(currentState.getTimeElapsed()) && weAreInDanger.isPresent()) {
//			moveToPosition = getDefensiveMoveList(idunnoShip.getPosition()).stream()
//					.max(Comparators.compareByDistance(weAreInDanger.get().getPosition()));
//		} else {
			
			
			Ship enemyShip = currentState.getEnemy(getEnemyName(currentState));
			Point enemyPos = enemyShip.getPosition();
			if (currentState.getMeteoriteStates().size() == 1
					|| currentState.getMeteoriteStates().stream().filter(closerToUsPredicate(idunnoShip, currentState.getEnemyShips())).allMatch(m -> m.getMeteoriteRadius() < 30)) {
			    moveToPosition = ratapadasPos(enemyPos).stream().min(Comparators.compareByDistance(idunnoShip.getPosition()));
			} else {
					moveToPosition = currentState.getMeteoriteStates()
							.stream()
							.max(compareByScore()) // teljesen ugyan az mint a régiben, csak comparatorral
							.map(Meteorite::getPosition);
	//			}
			}
//		}
		return moveToPosition;
	}
	
	private List<Point> ratapadasPos(Point enemyPos) {
		return List.of(
			new Point(enemyPos.x() - (double) game.getRocketExplosionRadius() *2., enemyPos.y() - (double) game.getRocketExplosionRadius() *2.),
			new Point(enemyPos.x() + (double) game.getRocketExplosionRadius() *2., enemyPos.y() + (double) game.getRocketExplosionRadius() *2.),
			new Point(enemyPos.x() + (double) game.getRocketExplosionRadius() *2., enemyPos.y() - (double) game.getRocketExplosionRadius() *2.),
			new Point(enemyPos.x() - (double) game.getRocketExplosionRadius() *2., enemyPos.y() + (double) game.getRocketExplosionRadius() *2.)
				);
	}
	private List<Point> getDefensiveMoveList(Point current) {
		return List.of(
			new Point(current.x() + game.getRocketExplosionRadius(), current.y()+ game.getRocketExplosionRadius()),
			new Point(current.x() - game.getRocketExplosionRadius(), current.y()- game.getRocketExplosionRadius()),
			new Point(current.x() + game.getRocketExplosionRadius(), current.y()- game.getRocketExplosionRadius()),
			new Point(current.x() - game.getRocketExplosionRadius(), current.y()+ game.getRocketExplosionRadius())
				);
	}
	private Optional<Rocket> weAreInDanger(GameState currentState) {
		// az a baj, hogy a mozgásunk nincs belekalkulálva igy akkor is kiírja, ha elmozgunk majd igy is ugy is
		Circle circle = new Circle(currentState.getIdunnoShip().getPosition(), game.getRocketExplosionRadius());
		return currentState.getRocketStates().stream()
			.filter(r -> !r.getOwner().equals(OUR_NAME))
			.filter(r -> Objects.nonNull(currentState.getRocketPaths().get(r.getRocketID()))
				&& CommonMethods.isIntersect(currentState.getRocketPaths().get(r.getRocketID()), circle))
			.findAny();
	}

	private Point fallBackMove() {
		return new Point(0, 0);
	}
	private Predicate<Meteorite> closerToUsPredicate(Ship ourShip, Collection<Ship> enemyShips) {
		return meteorite -> enemyShips.stream()
			.allMatch(enemyShip -> meteorite.getDistanceFromUs() / ourShip.getSpeed() < meteorite.getDistanceFromEnemy(enemyShip.getOwner()) / enemyShip.getSpeed());
	}
	private Optional<Point> getRocketMoveToPosition(GameState lastState, GameState currentState) {
//		if (rocketOnCooldown(currentState.getTimeElapsed())) {
//			return Optional.empty();
//		}
		Optional<Meteorite> closestMeteoritePosToEnemy = CommonMethods
				.getClosestMeteoritePosToEnemy(currentState.getMeteoriteStates(), currentState.getEnemy(getEnemyName(currentState)).getPosition(), getEnemyName(currentState));
		return getTarget(currentState, closestMeteoritePosToEnemy, lastState.getEnemy(getEnemyName(currentState)));
	}

	private String getEnemyName(GameState currentState) {
		return hateU.orElse(currentState.getClosestEnemy().getOwner());
	}
	private Optional<Point> getTarget(GameState gameStatus,
			Optional<Meteorite> closestMeteoritePosToEnemy, Ship enemyShipLastState) {
		Ship botShip = hateU != null? gameStatus.getEnemy(hateU.orElse(enemyShipLastState.getOwner())) : gameStatus.getEnemy(enemyShipLastState.getOwner());
		Optional<Point> target = Optional.empty();
			Point targetVelocity = calculateVelocity(enemyShipLastState, botShip);
			if (botShip.getPosition().distance(gameStatus.getIdunnoShip().getPosition()) < game.getRocketRange()) {
				target = getShootTargetPosition(1, gameStatus.getIdunnoShip().getPosition(), botShip.getPosition(), targetVelocity, closestMeteoritePosToEnemy)
						.filter(t -> willHitTarget(gameStatus.getIdunnoShip().getPosition(), t,
							gameStatus, closestMeteoritePosToEnemy))
						;
			}
			double closestmeteordistanceFromUs = closestMeteoritePosToEnemy.get().getDistanceFromUs();
			if (!target.isPresent() 
					&& gameStatus.getMeteoriteStates().size() > 1 
					&& closestmeteordistanceFromUs > game.getRocketExplosionRadius() + closestMeteoritePosToEnemy.get().getMeteoriteRadius()
					&& closestmeteordistanceFromUs < game.getRocketRange()
					&& closestmeteordistanceFromUs / gameStatus.getIdunnoShip().getSpeed() > closestMeteoritePosToEnemy.get().getDistanceFromEnemy(enemyShipLastState.getOwner()) / enemyShipLastState.getSpeed()
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
			.filter(m -> !excludedMeteor.filter(excluded -> excluded.getDistanceFromUs() > excluded.getDistanceFromEnemy(getEnemyName(state))).isPresent() || m.getMeteoriteID() != excludedMeteor.get().getMeteoriteID())
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
		int sumOfMeteoritePoints = currentState.getMeteoriteStates()
				.stream()
				.mapToInt(Meteorite::getMeteoriteRadius)
				.sum();
		return currentState.getOurScore() >= game.getUpgradeScore() && sumOfMeteoritePoints >= 2 * game.getUpgradeScore();
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
	    boolean aboutToHitUs = CommonMethods.distanceBetweenTwoPoint(ourPosition, rocket.getPosition()) <= (double) (game.getRocketExplosionRadius() / 2);
	    boolean aboutToHitMeteorite = gameStatus.getMeteoriteStates().stream()
	        .map(meteorite -> new Circle(meteorite.getPosition(), meteorite.getMeteoriteRadius()))
	        .filter(circle -> CommonMethods.isIntersect(rocketPath, circle))
	        .anyMatch(circle -> CommonMethods.distanceBetweenTwoPoint(rocket.getPosition(), circle.getCenter()) < circle.getRadius() + 2.);
        return aboutToHitMeteorite || aboutToReachEndOfPath || aboutToHitUs;
	}
}
