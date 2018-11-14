package idunno.spacescavanger.strategy;

import java.util.Optional;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Ship;

public abstract class Strategy {
	protected final Game game;
	private Optional<GameState> lastState;
	private final RocketPathCalculator rocketPathCalculator = new RocketPathCalculator();

	public Strategy(Game game) {
		this.game = game;
		lastState = Optional.empty();
	}

	public final GameResponse move(GameState gameState) {
		GameResponse response;
		gameState.setRocketPaths(rocketPathCalculator.calculate(lastState, gameState, game.getRocketRange()));
		if (lastState.isPresent())
			response = suggestMove(lastState.get(), gameState);
		else
			response = suggestFirstMove(gameState);
		setLastState(gameState);
		return response;
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

	protected Optional<Point> getShootTargetPosition(int i, Point shooter, Point target, Point targetVelocity) {
		if (i >= game.getRocketRange()) {
			return Optional.empty();
		}
		Point enemyNextCoordinates = target.add(targetVelocity);
		if (shooter.distance(enemyNextCoordinates) > (game.getRocketMovementSpeed() * i)) {
			i++;
			return getShootTargetPosition(i, shooter, enemyNextCoordinates, targetVelocity);
		} else {
			return Optional.of(enemyNextCoordinates);
		}
	}

}
