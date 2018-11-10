package idunno.spacescavanger.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GameState.Builder.class)
public class GameState {
	private final List<Meteorite> meteoriteStates;
	private final List<Rocket> rocketStates;
	private final List<Ship> shipStates;
	private final List<Standings> standings;
	private final GameStatus gameStatus;
	private final int timeElapsed;

	private GameState(Builder builder) {
		this.meteoriteStates = builder.meteoriteStates;
		this.rocketStates = builder.rocketStates;
		this.shipStates = builder.shipStates;
		this.standings = builder.standings;
		this.gameStatus = builder.gameStatus;
		this.timeElapsed = builder.timeElapsed;
	}

	public List<Meteorite> getMeteoriteStates() {
		return meteoriteStates;
	}


	public List<Rocket> getRocketStates() {
		return rocketStates;
	}

	public List<Ship> getShipStates() {
		return shipStates;
	}

	public List<Standings> getStandings() {
		return standings;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public int getTimeElapsed() {
		return timeElapsed;
	}

	public static Builder builder() {
		return new Builder();
	}
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof GameState)) {
			return false;
		}
		GameState castOther = (GameState) other;
		return Objects.equals(meteoriteStates, castOther.meteoriteStates)
				&& Objects.equals(rocketStates, castOther.rocketStates)
				&& Objects.equals(shipStates, castOther.shipStates) && Objects.equals(standings, castOther.standings)
				&& Objects.equals(gameStatus, castOther.gameStatus)
				&& Objects.equals(timeElapsed, castOther.timeElapsed);
	}

	@Override
	public int hashCode() {
		return Objects.hash(meteoriteStates, rocketStates, shipStates, standings, gameStatus, timeElapsed);
	}
	@Override
	public String toString() {
		return "GameState [meteoriteStates=" + meteoriteStates + ", rocketStates=" + rocketStates + ", shipStates="
				+ shipStates + ", standings=" + standings + ", gameStatus=" + gameStatus + ", timeElapsed="
				+ timeElapsed + "]";
	}

	public static final class Builder {
		private List<Meteorite> meteoriteStates = Collections.emptyList();
		private List<Rocket> rocketStates = Collections.emptyList();
		private List<Ship> shipStates = Collections.emptyList();
		private List<Standings> standings = Collections.emptyList();
		private GameStatus gameStatus;
		private int timeElapsed;

		private Builder() {
		}

		public Builder withMeteoriteStates(List<Meteorite> meteoriteStates) {
			this.meteoriteStates = meteoriteStates;
			return this;
		}

		public Builder withRocketStates(List<Rocket> rocketStates) {
			this.rocketStates = rocketStates;
			return this;
		}

		public Builder withShipStates(List<Ship> shipStates) {
			this.shipStates = shipStates;
			return this;
		}

		public Builder withStandings(List<Standings> standings) {
			this.standings = standings;
			return this;
		}

		public Builder withGameStatus(GameStatus gameStatus) {
			this.gameStatus = gameStatus;
			return this;
		}

		public Builder withTimeElapsed(int timeElapsed) {
			this.timeElapsed = timeElapsed;
			return this;
		}

		public GameState build() {
			return new GameState(this);
		}
	}

}
