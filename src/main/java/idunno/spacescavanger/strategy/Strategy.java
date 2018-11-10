package idunno.spacescavanger.strategy;

import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;

public abstract class Strategy {
	protected final Game game;

	public Strategy(Game game) {
		this.game = game;
	}

	public abstract GameResponse move(GameState gameStatus);

	public static class NoStrategy extends Strategy {

		public NoStrategy(Game game) {
			super(game);
		}

		@Override
		public GameResponse move(GameState gameStatus) {
			return GameResponse.builder()
					.withShipMoveToX(100)
					.withShipMoveToY(100)
					.build();
		}

	}
}
