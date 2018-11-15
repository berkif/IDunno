package idunno.spacescavanger.dto;

import static idunno.spacescavanger.strategy.Strategy.OUR_NAME;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.coordgeom.Line;

@JsonDeserialize(builder = GameState.Builder.class)
public class GameState {
	private final List<Meteorite> meteoriteStates;
	private final List<Rocket> rocketStates;
	private final Ship enemyShip;
	private final Ship idunnoShip;
	private final List<Standings> standings;
	private final GameStatus gameStatus;
	private final int timeElapsed;
	private final int ourScore;
	@JsonIgnore
	private Map<Integer, Line> rocketPaths;

	private GameState(Builder builder) {
		this.meteoriteStates = builder.meteoriteStates;
		this.rocketStates = builder.rocketStates;
		this.idunnoShip = builder.idunnoShip;
		this.enemyShip = builder.enemyShip;
		this.standings = builder.standings;
		this.gameStatus = builder.gameStatus;
		this.timeElapsed = builder.timeElapsed;
		this.ourScore = builder.ourScore;
		
	}

	public List<Meteorite> getMeteoriteStates() {
		return meteoriteStates;
	}


	public List<Rocket> getRocketStates() {
		return rocketStates;
	}

	public Ship getEnemyShip() {
		return enemyShip;
	}

	public Ship getIdunnoShip() {
		return idunnoShip;
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
	
	public int getOurScore() {
		return ourScore;
	}

	@JsonIgnore
	public Map<Integer, Line> getRocketPaths() {
	    return rocketPaths;
	}
	
	@JsonIgnore
	public void setRocketPaths(Map<Integer, Line> rocketPaths) {
	    this.rocketPaths = rocketPaths;
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
				&& Objects.equals(enemyShip, castOther.enemyShip) && Objects.equals(idunnoShip, castOther.idunnoShip)
				&& Objects.equals(standings, castOther.standings) && Objects.equals(gameStatus, castOther.gameStatus)
				&& Objects.equals(timeElapsed, castOther.timeElapsed)
				&& Objects.equals(rocketPaths, castOther.rocketPaths);
	}

	@Override
	public int hashCode() {
		return Objects.hash(meteoriteStates, rocketStates, enemyShip, idunnoShip, standings, gameStatus, timeElapsed,
			rocketPaths);
	}

	@Override
	public String toString() {
		return "GameState [meteoriteStates=" + meteoriteStates + ", rocketStates=" + rocketStates + ", enemyShip="
				+ enemyShip + ", idunnoShip=" + idunnoShip + ", standings=" + standings + ", gameStatus=" + gameStatus
				+ ", timeElapsed=" + timeElapsed + ", rocketPaths=" + rocketPaths + "]";
	}


	public static final class Builder {
		private List<Meteorite> meteoriteStates = Collections.emptyList();
		private List<Rocket> rocketStates = Collections.emptyList();
		private Ship enemyShip;
		private Ship idunnoShip;
		private List<Standings> standings = Collections.emptyList();
		private GameStatus gameStatus;
		private int timeElapsed;
		private int ourScore;

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
			// eddig is igy használtuk, akkor már legyen itt
			// persze, lehet hogy nem lesz jó ha több csapat lesz YAGNI!
			Map<Boolean, List<Ship>> ships = shipStates.stream().collect(Collectors.partitioningBy(s -> s.getOwner().equals("idunno")));
			this.idunnoShip = ships.get(true).get(0);
			this.enemyShip = ships.get(false).get(0);
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
			meteoriteStates
			.forEach(meteorite -> 
					meteorite.setDistance(meteorite.getPosition().distance(idunnoShip.getPosition())));
			rocketStates.forEach(rocket -> 
					rocket.setDistance(rocket.getPosition().distance(idunnoShip.getPosition())));
			ourScore = standings.stream()
				    .filter(standing -> standing.getUserID().equals(OUR_NAME))
				    .findAny()
				    .get()
				    .getScore();
			return new GameState(this);
		}
	}

}
