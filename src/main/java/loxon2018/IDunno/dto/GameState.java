package loxon2018.IDunno.dto;

import java.util.List;

public class GameState {
	private List<Meteorite> meteorits;
	private List<Rocket> rockets;
	private List<Ship> ships;
	private List<Standings> standings;
	private GameStatus gameStatus;
	private int timeElapsed;

	public List<Meteorite> getMeteorits() {
		return meteorits;
	}

	public void setMeteorits(List<Meteorite> meteorits) {
		this.meteorits = meteorits;
	}

	public List<Rocket> getRockets() {
		return rockets;
	}

	public void setRockets(List<Rocket> rockets) {
		this.rockets = rockets;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Standings> getStandings() {
		return standings;
	}

	public void setStandings(List<Standings> standings) {
		this.standings = standings;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(int timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

}
