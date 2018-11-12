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
import idunno.spacescavanger.dto.Ship;

public class DummyFeriDummyStrat extends Strategy {

	public DummyFeriDummyStrat(Game game) {
		super(game);
	}

	public GameResponse doMove(GameState gameStatus) {
		Map<String, Ship> shipsByOwner = gameStatus.getShipStates()
				.stream()
				.collect(Collectors.toMap(Ship::getOwner, identity()));
		Point ourPos = shipsByOwner.get("idunno").getPosition();
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
				.getClosestMeteoritePos(gameStatus.getMeteoriteStates(), shipsByOwner.get("bot1").getPosition());
		Optional<Point> targetRocket = getTarget(gameStatus, shipsByOwner, closestMeteoritePosToEnemy);
		return GameResponse.builder().withShipMoveToPosition(targetPos).withRocketMoveToPosition(targetRocket).build();
	}

	private Optional<Point> getTarget(GameState gameStatus, Map<String, Ship> shipsByOwner,
			Optional<Point> closestMeteoritePosToEnemy) {
		Optional<Point> target;
		if (willHitTarget(shipsByOwner.get("idunno").getPosition(), shipsByOwner.get("bot1").getPosition(),
				gameStatus)) {
			target = Optional.of(shipsByOwner.get("bot1").getPosition());
		} else if (willHitTarget(shipsByOwner.get("idunno").getPosition(), closestMeteoritePosToEnemy.orElse(null),
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
}