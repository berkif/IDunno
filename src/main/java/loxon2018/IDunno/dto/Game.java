package loxon2018.IDunno.dto;

import java.util.List;

public class Game {
    private int gameLength;// játék hossza, ms
    private int mapSizeX;// pálya méret
    private int mapSizeY; // pálya méret
    private int commandSchedule; // a játék ennyi milliszekundumonként dolgozza fel a beérkezett utasításokat
    private int internalSchedule; // a játék ennyi milliszekundumonként frissíti a játéktér belső
    private int broadcastSchedule; // a játék ennyi milliszekundumonként küldi el minden játékosnak az aktuális
                                   // játékállapotot
    private double rocketMovementSpeed; // rakéta sebessége. Ha másodperccel arányos időt szeretnénk megkapni,
                                        // akkor szorozzuk be 1000/ internalSchedule -el
    private int rocketLoadingSchedule; // rakéta visszatöltési idő másodpercben
    private int rocketExplosionRadius; // rakéta robbanási sugara
    private int rocketRange; // rakéta hatótávolsága
    private double shipMovementSpeed; // úrhajó sebessége. Ha másodperccel arányos időt szeretnénk megkapni,
                                      // akkor szorozzuk be 1000/ internalSchedule -el
    private int shipRedeploySchedule; // űrhajó újratermésének ideje másodpercben
    private int shipSize; // űrhajó mérete
    private int shieldUsingSchedule; // pajzs fenntartási idő
    private int shieldRenewingSchedule; // pajzs visszatöltődési idő másodpercben
    private int upgradeScore; // űrhajó fejlesztéséhez szükséges pontszám
    private double movementSpeedMultiplier;// fejlesztés utáni sebességszorzó

    private List<Meteorite> meteorites;
    private List<Player> players;
    private List<Ship> spaceships;

    public List<Meteorite> getMeteorites() {
        return meteorites;
    }

    public void setMeteorites(List<Meteorite> meteorites) {
        this.meteorites = meteorites;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Ship> getSpaceships() {
        return spaceships;
    }

    public void setSpaceships(List<Ship> spaceships) {
        this.spaceships = spaceships;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }

    public int getMapSizeX() {
        return mapSizeX;
    }

    public void setMapSizeX(int mapSizeX) {
        this.mapSizeX = mapSizeX;
    }

    public int getMapSizeY() {
        return mapSizeY;
    }

    public void setMapSizeY(int mapSizeY) {
        this.mapSizeY = mapSizeY;
    }

    public int getCommandSchedule() {
        return commandSchedule;
    }

    public void setCommandSchedule(int commandSchedule) {
        this.commandSchedule = commandSchedule;
    }

    public int getInternalSchedule() {
        return internalSchedule;
    }

    public void setInternalSchedule(int internalSchedule) {
        this.internalSchedule = internalSchedule;
    }

    public int getBroadcastSchedule() {
        return broadcastSchedule;
    }

    public void setBroadcastSchedule(int broadcastSchedule) {
        this.broadcastSchedule = broadcastSchedule;
    }

    public double getRocketMovementSpeed() {
        return rocketMovementSpeed;
    }

    public void setRocketMovementSpeed(double rocketMovementSpeed) {
        this.rocketMovementSpeed = rocketMovementSpeed;
    }

    public int getRocketLoadingSchedule() {
        return rocketLoadingSchedule;
    }

    public void setRocketLoadingSchedule(int rocketLoadingSchedule) {
        this.rocketLoadingSchedule = rocketLoadingSchedule;
    }

    public int getRocketExplosionRadius() {
        return rocketExplosionRadius;
    }

    public void setRocketExplosionRadius(int rocketExplosionRadius) {
        this.rocketExplosionRadius = rocketExplosionRadius;
    }

    public int getRocketRange() {
        return rocketRange;
    }

    public void setRocketRange(int rocketRange) {
        this.rocketRange = rocketRange;
    }

    public double getShipMovementSpeed() {
        return shipMovementSpeed;
    }

    public void setShipMovementSpeed(double shipMovementSpeed) {
        this.shipMovementSpeed = shipMovementSpeed;
    }

    public int getShipRedeploySchedule() {
        return shipRedeploySchedule;
    }

    public void setShipRedeploySchedule(int shipRedeploySchedule) {
        this.shipRedeploySchedule = shipRedeploySchedule;
    }

    public int getShipSize() {
        return shipSize;
    }

    public void setShipSize(int shipSize) {
        this.shipSize = shipSize;
    }

    public int getShieldUsingSchedule() {
        return shieldUsingSchedule;
    }

    public void setShieldUsingSchedule(int shieldUsingSchedule) {
        this.shieldUsingSchedule = shieldUsingSchedule;
    }

    public int getShieldRenewingSchedule() {
        return shieldRenewingSchedule;
    }

    public void setShieldRenewingSchedule(int shieldRenewingSchedule) {
        this.shieldRenewingSchedule = shieldRenewingSchedule;
    }

    public int getUpgradeScore() {
        return upgradeScore;
    }

    public void setUpgradeScore(int upgradeScore) {
        this.upgradeScore = upgradeScore;
    }

    public double getMovementSpeedMultiplier() {
        return movementSpeedMultiplier;
    }

    public void setMovementSpeedMultiplier(double movementSpeedMultiplier) {
        this.movementSpeedMultiplier = movementSpeedMultiplier;
    }

    @Override
    public String toString() {
        return "Game [gameLength=" + gameLength + ", mapSizeX=" + mapSizeX + ", mapSizeY=" + mapSizeY + ", commandSchedule=" + commandSchedule + ", internalSchedule="
                + internalSchedule + ", broadcastSchedule=" + broadcastSchedule + ", rocketMovementSpeed=" + rocketMovementSpeed + ", rocketLoadingSchedule="
                + rocketLoadingSchedule + ", rocketExplosionRadius=" + rocketExplosionRadius + ", rocketRange=" + rocketRange + ", shipMovementSpeed=" + shipMovementSpeed
                + ", shipRedeploySchedule=" + shipRedeploySchedule + ", shipSize=" + shipSize + ", shieldUsingSchedule=" + shieldUsingSchedule + ", shieldRenewingSchedule="
                + shieldRenewingSchedule + ", upgradeScore=" + upgradeScore + ", movementSpeedMultiplier=" + movementSpeedMultiplier + ", meteorites=" + meteorites + ", players="
                + players + ", spaceships=" + spaceships + "]";
    }

}
