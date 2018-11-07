package loxon2018.IDunno;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import com.fasterxml.jackson.databind.ObjectMapper;

import loxon2018.IDunno.dto.Game;
import loxon2018.IDunno.dto.GameResponse;
import loxon2018.IDunno.dto.GameState;

public class ClientEndpoint extends Endpoint implements MessageHandler.Whole<String> {
	private Session session;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		session.addMessageHandler(this);
		this.session = session;
	}

	// TODO ez csak a tesztelés miatt ilyen undorító
	public void onMessage(String message) {
		try {
			System.out.println(objectMapper.readValue(message, Game.class));
		} catch (IOException e) {
			try {
				System.out.println(objectMapper.readValue(message, GameState.class));
				GameResponse g = new GameResponse();
				g.setRocketMoveToX(null);
				g.setRocketMoveToY(null);
				g.setShipMoveToX(100);
				g.setShipMoveToY(100);
				String writeValueAsString = objectMapper.writeValueAsString(g);
				sendMessage(writeValueAsString);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void sendMessage(String message) {
		session.getAsyncRemote().sendText(message);
	}
}
