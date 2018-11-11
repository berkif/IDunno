package idunno.spacescavanger.strategy;

import static java.util.function.Function.identity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Meteorite;
import idunno.spacescavanger.dto.Ship;

public class DummyFeriDummyStrat extends Strategy {

	public DummyFeriDummyStrat(Game game) {
		super(game);
	}

	@Override
	public GameResponse move(GameState gameStatus) {
		Map<String, Ship> shipsByOwner = gameStatus.getShipStates().stream()
				.collect(Collectors.toMap(Ship::getOwner, identity()));
		List<Meteorite> highestPointsMeteorites = CommonMethods
				.getHighestPointsMeteorites(gameStatus.getMeteoriteStates());
		Optional<Position> closestMeteoritePos = CommonMethods.getClosestMeteoritePos(highestPointsMeteorites,
				shipsByOwner.get("idunno").getPosition());
		Optional<Position> closestMeteoritePosToEnemy = CommonMethods
				.getClosestMeteoritePos(gameStatus.getMeteoriteStates(), shipsByOwner.get("bot1").getPosition());
		return GameResponse.builder().withShipMoveToPosition(closestMeteoritePos.orElse(new Position(100, 100)))
				.withRocketMoveToPosition(closestMeteoritePosToEnemy).build();
	}

	@Override
	protected GameResponse suggestFirstMove(GameState currentState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GameResponse suggestMove(GameState lastState, GameState currentState) {
		// TODO Auto-generated method stub
		return null;
	}
}