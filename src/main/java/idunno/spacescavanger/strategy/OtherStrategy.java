package idunno.spacescavanger.strategy;

import static java.util.Optional.empty;

import java.util.Optional;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;

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

		return empty();
	}

	private Point fallBackMove() {
		return new Point(0, 0);
	}

	private Optional<Point> getRocketMoveToPosition(GameState lastState, GameState currentState) {

		return empty();
	}

	private boolean shouldUpgrade(GameState currentState) {
		return false;
	}

	private boolean shouldTurnOnShield(GameState currentState) {
		return false;
	}
}
