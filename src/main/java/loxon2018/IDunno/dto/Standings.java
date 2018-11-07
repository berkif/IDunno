package loxon2018.IDunno.dto;

public class Standings {

    private String userID;
    private int score;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Standings [userID=" + userID + ", score=" + score + "]";
    }

}
