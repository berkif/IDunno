package idunno.spacescavanger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ship {
    private int shipID;
    private double shipX;
    private double shipY;
    private boolean shieldIsActivated;
    @JsonProperty("isUpgraded")
    private boolean upgraded;
    private String owner;

    public int getShipID() {
        return shipID;
    }

    public void setShipID(int shipID) {
        this.shipID = shipID;
    }

    public double getShipX() {
        return shipX;
    }

    public void setShipX(double shipX) {
        this.shipX = shipX;
    }

    public double getShipY() {
        return shipY;
    }

    public void setShipY(double shipY) {
        this.shipY = shipY;
    }

    public boolean isShieldIsActivated() {
        return shieldIsActivated;
    }

    public void setShieldIsActivated(boolean shieldIsActivated) {
        this.shieldIsActivated = shieldIsActivated;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean isUpgraded) {
        this.upgraded = isUpgraded;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Ship [shipID=" + shipID + ", shipX=" + shipX + ", shipY=" + shipY + ", shieldIsActivated=" + shieldIsActivated + ", upgraded=" + upgraded + ", owner=" + owner
                + "]";
    }

}
