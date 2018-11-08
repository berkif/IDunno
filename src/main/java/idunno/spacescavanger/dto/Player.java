package idunno.spacescavanger.dto;

public class Player {
    private String userID;
    private String userName;
    private int raceID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    @Override
    public String toString() {
        return "Player [userID=" + userID + ", userName=" + userName + ", raceID=" + raceID + "]";
    }

}
