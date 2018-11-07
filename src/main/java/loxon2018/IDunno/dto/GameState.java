package loxon2018.IDunno.dto;

import java.util.List;

public class GameState {
	private List<Meteorite> meteoriteStates;
	private List<Rocket> rocketStates;
	private List<Ship> shipStates;
	private List<Standings> standings;
	private GameStatus gameStatus;
	private int timeElapsed;

	public List<Meteorite> getMeteoriteStates() {
		return meteoriteStates;
	}

	public void setMeteoriteStates(List<Meteorite> meteorits) {
		this.meteoriteStates = meteorits;
	}

	public List<Rocket> getRocketStates() {
		return rocketStates;
	}

	public void setRocketStates(List<Rocket> rockets) {
		this.rocketStates = rockets;
	}

	public List<Ship> getShipStates() {
		return shipStates;
	}

	public void setShipStates(List<Ship> ships) {
		this.shipStates = ships;
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

    @Override
    public String toString() {
        return "GameState [meteoriteStates=" + meteoriteStates + ", rocketStates=" + rocketStates + ", shipStates=" + shipStates + ", standings=" + standings + ", gameStatus="
                + gameStatus + ", timeElapsed=" + timeElapsed + "]";
    }
	
}
