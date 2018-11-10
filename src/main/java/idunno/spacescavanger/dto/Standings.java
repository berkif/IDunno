package idunno.spacescavanger.dto;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Standings.Builder.class)
public class Standings {

	private final String userID;
	private final int score;

	private Standings(Builder builder) {
		this.userID = builder.userID;
		this.score = builder.score;
	}

	public String getUserID() {
		return userID;
	}

	public int getScore() {
		return score;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Standings))
			return false;
		Standings castOther = (Standings) other;
		return Objects.equals(userID, castOther.userID) && Objects.equals(score, castOther.score);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userID, score);
	}

	@Override
	public String toString() {
		return "Standings [userID=" + userID + ", score=" + score + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String userID;
		private int score;

		private Builder() {
		}

		public Builder withUserID(String userID) {
			this.userID = userID;
			return this;
		}

		public Builder withScore(int score) {
			this.score = score;
			return this;
		}

		public Standings build() {
			return new Standings(this);
		}
	}

}
