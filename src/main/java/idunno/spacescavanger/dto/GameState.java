package idunno.spacescavanger.dto;

import static idunno.spacescavanger.strategy.Strategy.BOT_NAME;
import static idunno.spacescavanger.strategy.Strategy.OUR_NAME;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.coordgeom.Line;

@JsonDeserialize(builder = GameState.Builder.class)
public class GameState {
	private final List<Meteorite> meteoriteStates;
	private final List<Rocket> rocketStates;
	private final Map<String, Ship> enemyShips;
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
		this.enemyShips = builder.enemyShips;
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

	public Collection<Ship> getEnemyShips() {
		return enemyShips.values();
	}

	public Ship getAnyEnemy() {
		return this.getEnemyShips().stream().findAny().get();
	}

	public Ship getEnemy(String name) {
		return enemyShips.get(name);
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
				&& Objects.equals(enemyShips, castOther.enemyShips) && Objects.equals(idunnoShip, castOther.idunnoShip)
				&& Objects.equals(standings, castOther.standings) && Objects.equals(gameStatus, castOther.gameStatus)
				&& Objects.equals(timeElapsed, castOther.timeElapsed)
				&& Objects.equals(rocketPaths, castOther.rocketPaths);
	}

	@Override
	public int hashCode() {
		return Objects.hash(meteoriteStates, rocketStates, enemyShips, idunnoShip, standings, gameStatus, timeElapsed,
			rocketPaths);
	}

	@Override
	public String toString() {
		return "GameState [meteoriteStates=" + meteoriteStates + ", rocketStates=" + rocketStates + ", enemyShip="
				+ enemyShips + ", idunnoShip=" + idunnoShip + ", standings=" + standings + ", gameStatus=" + gameStatus
				+ ", timeElapsed=" + timeElapsed + ", rocketPaths=" + rocketPaths + "]";
	}


	public static final class Builder {
		private List<Meteorite> meteoriteStates = Collections.emptyList();
		private List<Rocket> rocketStates = Collections.emptyList();
		private Map<String, Ship> enemyShips = Collections.emptyMap();
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
			Map<String, Ship> ships = shipStates.stream().collect(toMap(Ship::getOwner, identity()));
			this.idunnoShip = ships.get(OUR_NAME);
			ships.remove(OUR_NAME);
			this.enemyShips = ships;
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
					meteorite.setDistanceFromUs(meteorite.getPosition().distance(idunnoShip.getPosition()))
						.setDistanceFromEnemy(BOT_NAME, meteorite.getPosition().distance(enemyShips.get(BOT_NAME).getPosition())));
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
