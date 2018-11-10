package idunno.spacescavanger;

import static java.util.Optional.empty;

import java.util.Optional;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
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
		strategy.ifPresentOrElse(s -> send(s.move(converter.toObject(message, GameState.class))),
			() -> initStrategy(message));
	}

	void send(GameResponse response) {
		sendMessage(converter.toMessage(response));
	}

	private void initStrategy(String message) {
		strategy = Optional.of(new Strategy.NoStrategy(converter.toObject(message, Game.class)));
	}

	public void sendMessage(String message) {
		session.getAsyncRemote()
				.sendText(message);
	}
}
