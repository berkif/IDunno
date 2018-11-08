package idunno.spacescavanger.dto;

public class Rocket {
    private int rocketID;
    private double rocketX;
    private double rocketY;
    private String owner;

    public int getRocketID() {
        return rocketID;
    }

    public void setRocketID(int rocketID) {
        this.rocketID = rocketID;
    }

    public double getRocketX() {
        return rocketX;
    }

    public void setRocketX(double rocketX) {
        this.rocketX = rocketX;
    }

    public double getRocketY() {
        return rocketY;
    }

    public void setRocketY(double rocketY) {
        this.rocketY = rocketY;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Rocket [rocketID=" + rocketID + ", rocketX=" + rocketX + ", rocketY=" + rocketY + ", owner=" + owner + "]";
    }

}
