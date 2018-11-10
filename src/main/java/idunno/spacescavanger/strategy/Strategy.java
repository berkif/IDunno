package idunno.spacescavanger.strategy;

import java.util.Optional;

import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;

public abstract class Strategy {
	protected final Game game;
	private Optional<GameState> lastState;

	public Strategy(Game game) {
		this.game = game;
		lastState = Optional.empty();
	}

	public GameResponse move(GameState gameState) {
		GameResponse response;
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

}
