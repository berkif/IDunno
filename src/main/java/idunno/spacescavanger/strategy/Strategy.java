package idunno.spacescavanger.strategy;

import java.util.Objects;
import java.util.Optional;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Meteorite;
import idunno.spacescavanger.dto.Ship;

public abstract class Strategy {
	public static final String OUR_NAME = "idunno";
	protected final Game game;
	private Optional<GameState> lastState;
	private final RocketPathCalculator rocketPathCalculator = new RocketPathCalculator();
	private int lastRocketLunchedAt = 0;
	private int lastShieldUsedAt = 0;
	public Strategy(Game game) {
		this.game = game;
		lastState = Optional.empty();
	}

	public final GameResponse move(GameState gameState) {
		GameResponse response;
		gameState.setRocketPaths(rocketPathCalculator.calculate(lastState, gameState, game.getRocketRange()));
		gameState.getIdunnoShip().calculateSpeed(game.getShipMovementSpeed(), game.getMovementSpeedMultiplier());
		gameState.getEnemy(gameState.getClosestEnemy().getOwner()).calculateSpeed(game.getShipMovementSpeed(), game.getMovementSpeedMultiplier());
		if (lastState.isPresent())
			response = suggestMove(lastState.get(), gameState);
		else
			response = suggestFirstMove(gameState);
		setLastState(gameState);
		if (!Objects.isNull(response.getRocketMoveToX())) {
			lastRocketLunchedAt = gameState.getTimeElapsed();
//			System.out.println("shooted at " + lastRocketLunchedAt);
		}
		if (response.isShieldIsActivated()) {
			lastShieldUsedAt = gameState.getTimeElapsed();
			System.out.println("shield used at " + lastRocketLunchedAt);
		}
		return response;
	}

	boolean shieldOnCooldown(int timeElapsed) {
		return lastShieldUsedAt > 0 && (timeElapsed - lastShieldUsedAt) / 1000 < game.getShieldRenewingSchedule();
	}
	boolean rocketOnCooldown(int timeElapsed) {
		return lastRocketLunchedAt > 0 && (timeElapsed - lastRocketLunchedAt) / 1000 < game.getRocketLoadingSchedule();
	}
	protected abstract GameResponse suggestFirstMove(GameState currentState);

	// nem tudom, hogy használható lesz e. Nincs semmi infó arról, hogy mi milyen
	// irányba halad,
	// az előző gamestateből viszont ki lehet számolni de nem tudm, hogy jó ötlet e.
	protected abstract GameResponse suggestMove(GameState lastState, GameState currentState);
	
	private void setLastState(GameState gameState) {
		lastState = Optional.of(gameState);
	}
	protected Point calculateVelocity(Ship lastShipState, Ship currentShipState) {
        return currentShipState.getPosition().substract(lastShipState.getPosition());
    }
	// nincs használva de meghagytam
    protected double calculateAngle(Point first, Point second) {
        Point diff = second.substract(first);
        return Math.atan(diff.y() / diff.x());
    }

	protected Optional<Point> getShootTargetPosition(int i, Point shooter, Point target, Point targetVelocity, Optional<Meteorite> closestMeteoriteToTarget) {
		Point enemyNextCoordinates = target.add(targetVelocity);
		if (i >= game.getRocketRange() || (closestMeteoriteToTarget.isPresent() && closestMeteoriteToTarget.get().getPosition().distance(enemyNextCoordinates) < closestMeteoriteToTarget.get().getMeteoriteRadius())) {
			return Optional.empty();
		}
		if (shooter.distance(enemyNextCoordinates) - game.getRocketExplosionRadius() > (game.getRocketMovementSpeed() * i)) {
			i++;
			return getShootTargetPosition(i, shooter, enemyNextCoordinates, targetVelocity, closestMeteoriteToTarget);
		} else {
			return Optional.of(enemyNextCoordinates);
		}
	}

}
