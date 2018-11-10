package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByDistance;
import static java.util.function.Function.identity;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Meteorite;
import idunno.spacescavanger.dto.Ship;

public abstract class Strategy {
	protected final Game game;

	public Strategy(Game game) {
		this.game = game;
	}

	public abstract GameResponse move(GameState gameStatus);

	// TODO delete this.
	public static class NoStrategy extends Strategy {

		public NoStrategy(Game game) {
			super(game);
		}

		@Override
		public GameResponse move(GameState gameStatus) {
			Map<String, Ship> shipsByOwner = gameStatus.getShipStates()
					.stream()
					.collect(Collectors.toMap(Ship::getOwner, identity()));
			Optional<Position> min = gameStatus.getMeteoriteStates()
							.stream()
							.map(Meteorite::getPosition)
							.min(compareByDistance(shipsByOwner.get("idunno")
							.getPosition()));
			return GameResponse.builder()
					.withShipMoveToPosition(min.orElse(new Position(100, 100)))
					.withRocketMoveToPosition(shipsByOwner.get("bot1")
							.getPosition())
					.build();
		}

	}
}
