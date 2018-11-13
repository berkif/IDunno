package idunno.spacescavanger;

import static idunno.spacescavanger.dto.GameStatus.ABORTED;
import static idunno.spacescavanger.dto.GameStatus.ENDED;
import static idunno.spacescavanger.strategy.Comparators.compareByDistance;
import static java.util.Optional.empty;
import static java.util.function.Function.identity;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.GameStatus;
import idunno.spacescavanger.dto.Meteorite;
import idunno.spacescavanger.dto.Ship;
import idunno.spacescavanger.strategy.DummyFeriDummyStrat;
import idunno.spacescavanger.strategy.Strategy;

public class ClientEndpoint extends Endpoint implements MessageHandler.Whole<String> {
	private Session session;
	private Optional<Strategy> strategy = empty();
	private final MessageConverter converter = new MessageConverter();

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		session.addMessageHandler(this);
		this.session = session;
	}

	@Override
	public void onMessage(String message) {
		try {
			strategy.ifPresentOrElse(s -> {
				GameState status = converter.toObject(message, GameState.class);
				GameStatus gameStatus = status.getGameStatus();
				System.out.println(status.getTimeElapsed());
				if (gameStatus == ABORTED || gameStatus == ENDED) {
					System.out.println(gameStatus);
					stop();
				}
				send(s.move(status));
			}, () -> initStrategy(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void stop() {
		sendMessage("{\"stopGame\":\"STOP\"}");
	}

	void send(GameResponse response) {
		sendMessage(converter.toMessage(response));
	}

	private void initStrategy(String message) {
		strategy = Optional.of(new DummyFeriDummyStrat(converter.toObject(message, Game.class)));
	}

	public void sendMessage(String message) {
		session.getAsyncRemote().sendText(message);
	}

	// TODO delete this.
	public static class NoStrategy extends Strategy {

		public NoStrategy(Game game) {
			super(game);
		}

		@Override
		public GameResponse suggestMove(GameState lastState, GameState currentState) {
			return suggestFirstMove(currentState);
		}

		@Override
		protected GameResponse suggestFirstMove(GameState currentState) {
			Map<String, Ship> shipsByOwner = currentState.getShipStates()
					.stream()
					.collect(Collectors.toMap(Ship::getOwner, identity()));
			Optional<Point> min = currentState.getMeteoriteStates()
					.stream()
					.map(Meteorite::getPosition)
					.min(compareByDistance(shipsByOwner.get("idunno").getPosition()));
			return GameResponse.builder()
					.withShipMoveToPosition(min.orElse(new Point(100, 100)))
					.withRocketMoveToPosition(shipsByOwner.get("bot1").getPosition())
					.build();
		}

	}

}
