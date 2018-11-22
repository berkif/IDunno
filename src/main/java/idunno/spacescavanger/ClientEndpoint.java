package idunno.spacescavanger;

import static idunno.spacescavanger.dto.GameStatus.ABORTED;
import static idunno.spacescavanger.dto.GameStatus.ENDED;
import static java.util.Optional.empty;

import java.util.Optional;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.GameStatus;
import idunno.spacescavanger.strategy.OtherStrategy;
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
				System.out.println(status.getTimeElapsed());
				GameStatus gameStatus = status.getGameStatus();
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
		System.out.println("init strategy");
		strategy = Optional.of(new OtherStrategy(converter.toObject(message, Game.class)));
	}

	public void sendMessage(String message) {
		session.getAsyncRemote().sendText(message);
	}


}
