package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Player.Builder.class)
public class Player {
	private final String userID;
	private final String userName;
	private final int raceID;

	private Player(Builder builder) {
		this.userID = builder.userID;
		this.userName = builder.userName;
		this.raceID = builder.raceID;
	}

	public String getUserID() {
		return userID;
	}


	public String getUserName() {
		return userName;
	}

	public int getRaceID() {
		return raceID;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Player)) {
			return false;
		}
		Player castOther = (Player) other;
		return Objects.equals(userID, castOther.userID) && Objects.equals(userName, castOther.userName)
				&& Objects.equals(raceID, castOther.raceID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userID, userName, raceID);
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public String toString() {
		return "Player [userID=" + userID + ", userName=" + userName + ", raceID=" + raceID + "]";
	}

	public static final class Builder {
		private String userID;
		private String userName;
		private int raceID;

		private Builder() {
		}

		public Builder withUserID(String userID) {
			this.userID = userID;
			return this;
		}

		public Builder withUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder withRaceID(int raceID) {
			this.raceID = raceID;
			return this;
		}

		public Player build() {
			return new Player(this);
		}
	}


}
