package idunno.spacescavanger.dto;

import static java.util.function.Function.identity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import idunno.spacescavanger.coordgeom.Point;

@JsonDeserialize(builder = Game.Builder.class)
public class Game {
	private final long timeElapsed;
	private final int gameLength;// játék hossza, ms
	private final int mapSizeX;// pálya méret
	private final int mapSizeY; // pálya méret
	private final int commandSchedule; // a játék ennyi milliszekundumonként dolgozza fel a beérkezett utasításokat
	private final int internalSchedule; // a játék ennyi milliszekundumonként frissíti a játéktér belső
	private final int broadcastSchedule; // a játék ennyi milliszekundumonként küldi el minden játékosnak az aktuális
	// játékállapotot
	private final double rocketMovementSpeed; // rakéta sebessége. Ha másodperccel arányos időt szeretnénk megkapni,
	// akkor szorozzuk be 1000/ internalSchedule -el
	private final int rocketLoadingSchedule; // rakéta visszatöltési idő másodpercben
	private final int rocketExplosionRadius; // rakéta robbanási sugara
	private final int rocketRange; // rakéta hatótávolsága
	private final double shipMovementSpeed; // úrhajó sebessége. Ha másodperccel arányos időt szeretnénk megkapni,
	// akkor szorozzuk be 1000/ internalSchedule -el
	private final int shipRedeploySchedule; // űrhajó újratermésének ideje másodpercben
	private final int shipSize; // űrhajó mérete
	private final int shieldUsingSchedule; // pajzs fenntartási idő
	private final int shieldRenewingSchedule; // pajzs visszatöltődési idő másodpercben
	private final int upgradeScore; // űrhajó fejlesztéséhez szükséges pontszám
	private final double movementSpeedMultiplier;// fejlesztés utáni sebességszorzó

	private final List<Meteorite> meteorites;
	private final List<Player> players;
	private final Map<String, Ship> startingPositions;

	private Game(Builder builder) {
		this.timeElapsed = builder.timeElapsed;
		this.gameLength = builder.gameLength;
		this.mapSizeX = builder.mapSizeX;
		this.mapSizeY = builder.mapSizeY;
		this.commandSchedule = builder.commandSchedule;
		this.internalSchedule = builder.internalSchedule;
		this.broadcastSchedule = builder.broadcastSchedule;
		this.rocketMovementSpeed = builder.rocketMovementSpeed;
		this.rocketLoadingSchedule = builder.rocketLoadingSchedule;
		this.rocketExplosionRadius = builder.rocketExplosionRadius;
		this.rocketRange = builder.rocketRange;
		this.shipMovementSpeed = builder.shipMovementSpeed;
		this.shipRedeploySchedule = builder.shipRedeploySchedule;
		this.shipSize = builder.shipSize;
		this.shieldUsingSchedule = builder.shieldUsingSchedule;
		this.shieldRenewingSchedule = builder.shieldRenewingSchedule;
		this.upgradeScore = builder.upgradeScore;
		this.movementSpeedMultiplier = builder.movementSpeedMultiplier;
		this.meteorites = builder.meteorites;
		this.players = builder.players;
		this.startingPositions = builder.startingPositions;
	}

	public long getTimeElapsed() {
		return timeElapsed;
	}

	public int getGameLength() {
		return gameLength;
	}

	public int getMapSizeX() {
		return mapSizeX;
	}

	public int getMapSizeY() {
		return mapSizeY;
	}

	public int getCommandSchedule() {
		return commandSchedule;
	}

	public int getInternalSchedule() {
		return internalSchedule;
	}

	public int getBroadcastSchedule() {
		return broadcastSchedule;
	}

	public double getRocketMovementSpeed() {
		return rocketMovementSpeed;
	}

	public int getRocketLoadingSchedule() {
		return rocketLoadingSchedule;
	}

	public int getRocketExplosionRadius() {
		return rocketExplosionRadius;
	}

	public int getRocketRange() {
		return rocketRange;
	}

	public double getShipMovementSpeed() {
		return shipMovementSpeed;
	}

	public int getShipRedeploySchedule() {
		return shipRedeploySchedule;
	}

	public int getShipSize() {
		return shipSize;
	}

	public int getShieldUsingSchedule() {
		return shieldUsingSchedule;
	}

	public int getShieldRenewingSchedule() {
		return shieldRenewingSchedule;
	}

	public int getUpgradeScore() {
		return upgradeScore;
	}

	public double getMovementSpeedMultiplier() {
		return movementSpeedMultiplier;
	}

	public List<Meteorite> getMeteorites() {
		return meteorites;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Point getStartingPosition(String teamName) {
		return startingPositions.get(teamName).getPosition();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Game)) {
			return false;
		}
		Game castOther = (Game) other;
		return Objects.equals(timeElapsed, castOther.timeElapsed) && Objects.equals(gameLength, castOther.gameLength)
				&& Objects.equals(mapSizeX, castOther.mapSizeX) && Objects.equals(mapSizeY, castOther.mapSizeY)
				&& Objects.equals(commandSchedule, castOther.commandSchedule)
				&& Objects.equals(internalSchedule, castOther.internalSchedule)
				&& Objects.equals(broadcastSchedule, castOther.broadcastSchedule)
				&& Objects.equals(rocketMovementSpeed, castOther.rocketMovementSpeed)
				&& Objects.equals(rocketLoadingSchedule, castOther.rocketLoadingSchedule)
				&& Objects.equals(rocketExplosionRadius, castOther.rocketExplosionRadius)
				&& Objects.equals(rocketRange, castOther.rocketRange)
				&& Objects.equals(shipMovementSpeed, castOther.shipMovementSpeed)
				&& Objects.equals(shipRedeploySchedule, castOther.shipRedeploySchedule)
				&& Objects.equals(shipSize, castOther.shipSize)
				&& Objects.equals(shieldUsingSchedule, castOther.shieldUsingSchedule)
				&& Objects.equals(shieldRenewingSchedule, castOther.shieldRenewingSchedule)
				&& Objects.equals(upgradeScore, castOther.upgradeScore)
				&& Objects.equals(movementSpeedMultiplier, castOther.movementSpeedMultiplier)
				&& Objects.equals(meteorites, castOther.meteorites) && Objects.equals(players, castOther.players)
				&& Objects.equals(startingPositions, castOther.startingPositions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(timeElapsed, gameLength, mapSizeX, mapSizeY, commandSchedule, internalSchedule,
				broadcastSchedule, rocketMovementSpeed, rocketLoadingSchedule, rocketExplosionRadius, rocketRange,
				shipMovementSpeed, shipRedeploySchedule, shipSize, shieldUsingSchedule, shieldRenewingSchedule,
				upgradeScore, movementSpeedMultiplier, meteorites, players, startingPositions);
	}

	@Override
	public String toString() {
		return "Game [timeElapsed=" + timeElapsed + ", gameLength=" + gameLength + ", mapSizeX=" + mapSizeX
				+ ", mapSizeY=" + mapSizeY + ", commandSchedule=" + commandSchedule + ", internalSchedule="
				+ internalSchedule + ", broadcastSchedule=" + broadcastSchedule + ", rocketMovementSpeed="
				+ rocketMovementSpeed + ", rocketLoadingSchedule=" + rocketLoadingSchedule + ", rocketExplosionRadius="
				+ rocketExplosionRadius + ", rocketRange=" + rocketRange + ", shipMovementSpeed=" + shipMovementSpeed
				+ ", shipRedeploySchedule=" + shipRedeploySchedule + ", shipSize=" + shipSize + ", shieldUsingSchedule="
				+ shieldUsingSchedule + ", shieldRenewingSchedule=" + shieldRenewingSchedule + ", upgradeScore="
				+ upgradeScore + ", movementSpeedMultiplier=" + movementSpeedMultiplier + ", meteorites=" + meteorites
				+ ", players=" + players + ", spaceships=" + startingPositions + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private long timeElapsed;
		private int gameLength;
		private int mapSizeX;
		private int mapSizeY;
		private int commandSchedule;
		private int internalSchedule;
		private int broadcastSchedule;
		private double rocketMovementSpeed;
		private int rocketLoadingSchedule;
		private int rocketExplosionRadius;
		private int rocketRange;
		private double shipMovementSpeed;
		private int shipRedeploySchedule;
		private int shipSize;
		private int shieldUsingSchedule;
		private int shieldRenewingSchedule;
		private int upgradeScore;
		private double movementSpeedMultiplier;
		private List<Meteorite> meteorites = Collections.emptyList();
		private List<Player> players = Collections.emptyList();
		private Map<String, Ship> startingPositions = Collections.emptyMap();

		private Builder() {
		}

		public Builder withTimeElapsed(long timeElapsed) {
			this.timeElapsed = timeElapsed;
			return this;
		}

		public Builder withGameLength(int gameLength) {
			this.gameLength = gameLength;
			return this;
		}

		public Builder withMapSizeX(int mapSizeX) {
			this.mapSizeX = mapSizeX;
			return this;
		}

		public Builder withMapSizeY(int mapSizeY) {
			this.mapSizeY = mapSizeY;
			return this;
		}

		public Builder withCommandSchedule(int commandSchedule) {
			this.commandSchedule = commandSchedule;
			return this;
		}

		public Builder withInternalSchedule(int internalSchedule) {
			this.internalSchedule = internalSchedule;
			return this;
		}

		public Builder withBroadcastSchedule(int broadcastSchedule) {
			this.broadcastSchedule = broadcastSchedule;
			return this;
		}

		public Builder withRocketMovementSpeed(double rocketMovementSpeed) {
			this.rocketMovementSpeed = rocketMovementSpeed;
			return this;
		}

		public Builder withRocketLoadingSchedule(int rocketLoadingSchedule) {
			this.rocketLoadingSchedule = rocketLoadingSchedule;
			return this;
		}

		public Builder withRocketExplosionRadius(int rocketExplosionRadius) {
			this.rocketExplosionRadius = rocketExplosionRadius;
			return this;
		}

		public Builder withRocketRange(int rocketRange) {
			this.rocketRange = rocketRange;
			return this;
		}

		public Builder withShipMovementSpeed(double shipMovementSpeed) {
			this.shipMovementSpeed = shipMovementSpeed;
			return this;
		}

		public Builder withShipRedeploySchedule(int shipRedeploySchedule) {
			this.shipRedeploySchedule = shipRedeploySchedule;
			return this;
		}

		public Builder withShipSize(int shipSize) {
			this.shipSize = shipSize;
			return this;
		}

		public Builder withShieldUsingSchedule(int shieldUsingSchedule) {
			this.shieldUsingSchedule = shieldUsingSchedule;
			return this;
		}

		public Builder withShieldRenewingSchedule(int shieldRenewingSchedule) {
			this.shieldRenewingSchedule = shieldRenewingSchedule;
			return this;
		}

		public Builder withUpgradeScore(int upgradeScore) {
			this.upgradeScore = upgradeScore;
			return this;
		}

		public Builder withMovementSpeedMultiplier(double movementSpeedMultiplier) {
			this.movementSpeedMultiplier = movementSpeedMultiplier;
			return this;
		}

		public Builder withMeteorites(List<Meteorite> meteorites) {
			this.meteorites = meteorites;
			return this;
		}

		public Builder withPlayers(List<Player> players) {
			this.players = players;
			return this;
		}

		public Builder withSpaceships(List<Ship> spaceships) {
			this.startingPositions = spaceships.stream()
					.collect(Collectors.toMap(Ship::getOwner, identity()));
			return this;
		}

		public Game build() {
			return new Game(this);
		}
	}

}
