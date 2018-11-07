package loxon2018.IDunno.dto;

public class Meteorite {
    private int meteoriteRadius;
    private int meteoriteID;
    private double meteoriteX;
    private double meteoriteY;

    public int getMeteoriteRadius() {
        return meteoriteRadius;
    }

    public void setMeteoriteRadius(int meteoriteRadius) {
        this.meteoriteRadius = meteoriteRadius;
    }

    public int getMeteoriteID() {
        return meteoriteID;
    }

    public void setMeteoriteID(int meteoriteID) {
        this.meteoriteID = meteoriteID;
    }

    public double getMeteoriteX() {
        return meteoriteX;
    }

    public void setMeteoriteX(double meteoriteX) {
        this.meteoriteX = meteoriteX;
    }

    public double getMeteoriteY() {
        return meteoriteY;
    }

    public void setMeteoriteY(double meteoriteY) {
        this.meteoriteY = meteoriteY;
    }

    @Override
    public String toString() {
        return "Meteorite [meteoriteRadius=" + meteoriteRadius + ", meteoriteID=" + meteoriteID + ", meteoriteX=" + meteoriteX + ", meteoriteY=" + meteoriteY + "]";
    }

}
