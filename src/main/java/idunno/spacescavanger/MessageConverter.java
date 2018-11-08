package idunno.spacescavanger;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import idunno.spacescavanger.dto.GameResponse;

public class MessageConverter {
	private final ObjectMapper mapper;

	MessageConverter() {
		mapper = new ObjectMapper();
	}

	String toMessage(GameResponse response) {
		try {
			return mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("error parsing GameResponse");
		}
	}

	<T> T toObject(String message, Class<T> type) {
		try {
			T converted = mapper.readValue(message, type);
			System.out.println(converted);
			return converted;
		} catch (IOException e) {
			throw new IllegalArgumentException("Error parsing server response.");
		}

	}
}